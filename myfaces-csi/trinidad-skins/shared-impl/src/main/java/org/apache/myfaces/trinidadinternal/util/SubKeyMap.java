/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.trinidadinternal.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.myfaces.trinidad.logging.TrinidadLogger;

/**
 * Map that wraps another to provide an isolated namespace using
 * a prefix.  This is especially handy for storing properties on
 * the session in a structured manner without putting them into
 * a true "Map" - because storing in a Map breaks session failover.
 * (Session failover won't trigger on mutations of contained objects.)
 * <p>
 * Note that there is a potential design flaw;  if you create a SubKeyMap
 * for "mypackage.foo" and for "mypackage.foo.bar", all the keys in the
 * latter will actually show up in the former (prefixed by ".bar").  This
 * "flaw" is actually relied on by PageFlowScopeMap (since it provides
 * a handy way to clear out all descendents), so don't "fix" it!
 */
final public class SubKeyMap extends AbstractMap<String, Object>
{
  public SubKeyMap(Map<String, Object> base, String prefix)
  {
    if (base == null)
      throw new NullPointerException();
    if (prefix == null)
      throw new NullPointerException();

    // Optimize the scenario where we're wrapping another SubKeyMap
    if (base instanceof SubKeyMap)
    {
      _base = ((SubKeyMap) base)._base;
      _prefix = ((SubKeyMap) base)._prefix + prefix;
    }
    else
    {
      _base = base;
      _prefix = prefix;
    }
  }

  @Override
  public boolean isEmpty()
  {
    return entrySet().isEmpty();
  }

  @Override
  public Object get(Object key)
  {
    key = _getBaseKey(key);
    return _base.get(key);
  }

  @Override
  public Object put(String key, Object value)
  {
    key = _getBaseKey(key);
    return _base.put(key, value);
  }

  @Override
  public Object remove(Object key)
  {
    key = _getBaseKey(key);
    _LOG.finest("Removing {0}", key);
    return _base.remove(key);
  }

  @Override
  public boolean containsKey(Object key)
  {
    if (!(key instanceof String))
      return false;

    return _base.containsKey(_getBaseKey(key));
  }

  @Override
  public Set<Map.Entry<String, Object>> entrySet()
  {
    if (_entrySet == null)
      _entrySet = new Entries();
    return _entrySet;
  }

  private String _getBaseKey(Object key)
  {
    if (key == null)
      throw new NullPointerException();
    // Yes, I want a ClassCastException if it's not a String
    return _prefix + ((String) key);
  }

  private List<String> _gatherKeys()
  {
    List<String> list = new ArrayList<String>();
    for(String key : _base.keySet())
    {
      if (key != null && key.startsWith(_prefix))
        list.add(key);
    }

    return list;
  }

  //
  // Set implementation for SubkeyMap.entrySet()
  //
  private class Entries extends AbstractSet<Map.Entry<String, Object>>
  {
    public Entries()
    {
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator()
    {
      // Sadly, if you just try to use a filtering approach
      // on the iterator, you'll get concurrent modification
      // exceptions.  Consequently, gather the keys in a list
      // and iterator over that.
      List<String> keyList = _gatherKeys();
      return new EntryIterator(keyList.iterator());
    }

    @Override
    public int size()
    {
      int size = 0;
      for(String key : _base.keySet())
      {
        if (key != null && key.startsWith(_prefix))
          size++;
      }

      return size;
    }

    @Override
    public boolean isEmpty()
    {
      Iterator<String> keys = _base.keySet().iterator();
      while (keys.hasNext())
      {
        String key = keys.next();
        // Short-circuit:  the default implementation would always
        // need to iterate to find the total size.
        if (key != null && key.startsWith(_prefix))
          return false;
      }

      return true;
    }

    @Override
    public void clear()
    {
      Iterator<String> keys = _base.keySet().iterator();
      while (keys.hasNext())
      {
        String key = keys.next();
        if (key != null && key.startsWith(_prefix))
        {
          _LOG.finest("Clearing out {0}", key);
          keys.remove();
        }
      }
    }
  }

  private class EntryIterator implements Iterator<Map.Entry<String, Object>>
  {
    public EntryIterator(Iterator<String> iterator)
    {
      _iterator = iterator;
    }

    public boolean hasNext()
    {
      return _iterator.hasNext();
    }

    public Map.Entry<String, Object> next()
    {
      String baseKey = _iterator.next();
      _currentKey = baseKey;
      return new Entry(baseKey);
    }

    public void remove()
    {
      if (_currentKey == null)
        throw new IllegalStateException();

      _base.remove(_currentKey);

      _currentKey = null;
    }

    private Iterator<String>  _iterator;
    private String    _currentKey;
  }

  private class Entry implements Map.Entry<String, Object>
  {
    public Entry(String baseKey)
    {
      _baseKey = baseKey;
    }

    public String getKey()
    {
      if (_key == null)
        _key = _baseKey.substring(_prefix.length());
      return _key;
    }

    public Object getValue()
    {
      return _base.get(_baseKey);
    }

    public Object setValue(Object value)
    {
      return _base.put(_baseKey, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o)
    {
      if (!(o instanceof Map.Entry))
        return false;
      Map.Entry<String, Object> e = (Map.Entry<String, Object>)o;
      return _equals(getKey(), e.getKey()) &&
      _equals(getValue(), e.getValue());
    }

    @Override
    public int hashCode()
    {
      Object key = getKey();
      Object value = getValue();
      return ((key   == null)   ? 0 :   key.hashCode()) ^
      ((value == null)   ? 0 : value.hashCode());
    }

    private String _baseKey;
    private String _key;
  }

  static private boolean _equals(Object a, Object b)
  {
    if (a == null)
      return b == null;
    return a.equals(b);
  }

  private final Map<String, Object> _base;
  private final String _prefix;
  private Set<Map.Entry<String, Object>> _entrySet;

  static private final TrinidadLogger _LOG = TrinidadLogger.createTrinidadLogger(SubKeyMap.class);
}
