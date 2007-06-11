package org.apache.myfaces.custom.skin;

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


import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.logging.TrinidadLogger;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidadinternal.application.StateManagerImpl;
import org.apache.myfaces.trinidadinternal.context.DialogServiceImpl;
import org.apache.myfaces.trinidadinternal.context.TrinidadPhaseListener;
import org.apache.myfaces.trinidadinternal.renderkit.core.CoreRenderingContext;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.PartialPageUtils;

public class SkinRenderKit extends RenderKit implements
		ExtendedRenderKitService {

	private static final Log log = LogFactory.getLog(SkinRenderKit.class);

	public RenderKit _delegate;
	
	public Map _map;
	
	public SkinRenderKit(){
		RenderKitFactory factory = (RenderKitFactory)
		    FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);		
		_delegate = factory.getRenderKit(FacesContext.getCurrentInstance(), 
				RenderKitFactory.HTML_BASIC_RENDER_KIT); 
		_map = new HashMap();
		log.info("SkinRenderer Created:" + _delegate);
	}
	
	public SkinRenderKit(RenderKit delegate) {
		log.info("SkinRenderer Created:" + delegate);
		_delegate = delegate;
		_map = new HashMap();
	}

	@Override
	public void addRenderer(String family, String rendererType,
			Renderer renderer) {
		//In this case, first check if this class inherits from
		//SkinRenderer class
		log.info("addRenderer "+renderer.getClass());
		if (SkinRenderer.class.isAssignableFrom(renderer.getClass())){		
			SkinRenderer sr = (SkinRenderer) renderer;
			_map.put(family+";"+rendererType, sr);		
		}else{
			_delegate.addRenderer(family, rendererType, renderer);
		}
	}

	@Override
	public ResponseStream createResponseStream(OutputStream out) {
		return _delegate.createResponseStream(out);
	}

	@Override
	public ResponseWriter createResponseWriter(Writer writer,
			String contentTypeList, String characterEncoding) {
		return _delegate.createResponseWriter(writer, contentTypeList,
				characterEncoding);
	}

	@Override
	public Renderer getRenderer(String family, String rendererType) {
		//First get a renderer from here
		Renderer r = (Renderer)_map.get(family+";"+rendererType);
		if (r == null){
			//For now i want to define a generic renderer wrapper
			//to components not defined. It should not harm anyone
			r = _delegate.getRenderer(family, rendererType);
			if (r != null){
				SkinRenderer sr = new GenericSkinRenderer(r); 
				_map.put(family+";"+rendererType, sr);
				return sr;				
			}else{
				return r;
			}
		}else{
			//I get a renderer
			if (SkinRenderer.class.isAssignableFrom(r.getClass())){
				//check if it has a delegate renderer
				SkinRenderer sr = (SkinRenderer) r;
				if (sr.getDelegate() == null){
					//set a proper delegate
					r = _delegate.getRenderer(family, rendererType);
					if (r != null){
						sr.setDelegate(r);
						return sr;
					}else{
						return r;
					}
				}
				return sr;
			}else{
				return r;
			}
		}		
	}

	@Override
	public ResponseStateManager getResponseStateManager() {
		// TODO Auto-generated method stub
		return _delegate.getResponseStateManager();
	}

	// METHODS INHERITED FROM ExtendedRenderKitService
	// The idea here is to implement or delegate the methods to appropiate
	// ExtendedRenderKitService.

	public void addScript(FacesContext context, String script) {
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {
			((ExtendedRenderKitService) _delegate).addScript(context, script);
			return;
		}

		if ((script == null) || "".equals(script))
			return;

		// Bulletproof against coders who don't include semicolons.
		// We end up concatenating all the scripts, so it would go poorly
		// if one didn't end with a semicolon
		if (!script.endsWith(";"))
			script += ";";

		_getScriptList(context, true).add(script);
	}

	@SuppressWarnings("unchecked")
	private List<String> _getScriptList(FacesContext context,
			boolean createIfNew) {
		Map<String, Object> requestMap = context.getExternalContext()
				.getRequestMap();

		List<String> l = (List<String>) requestMap.get(_SCRIPT_LIST_KEY);

		if ((l == null) && createIfNew) {
			l = new ArrayList<String>();
			requestMap.put(_SCRIPT_LIST_KEY, l);
		}

		return l;
	}

	static private final String _SCRIPT_LIST_KEY = "org.apache.myfaces.trinidadinternal.renderkit.ScriptList";

	public void encodeBegin(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {
			((ExtendedRenderKitService) _delegate).encodeBegin(context);
			return;
		}
		new CoreRenderingContext();
		DialogServiceImpl.pinPriorState(context);
	}

	public void encodeEnd(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {
			((ExtendedRenderKitService) _delegate).encodeEnd(context);
			return;
		}

	}

	public void encodeFinally(FacesContext context) {
		// TODO Auto-generated method stub
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {
			((ExtendedRenderKitService) _delegate).encodeFinally(context);
			return;
		}		
		
	    // Get the state token from the StateManager (if one is available)
	    String stateToken = StateManagerImpl.getStateToken(context);
	    // And push it onto the DialogService (in case we launched anything)
	    DialogServiceImpl.writeCurrentStateToken(context, stateToken);
	    
	    RenderingContext arc = RenderingContext.getCurrentInstance();
	    if (arc != null)
	    {
	      arc.release();
	    }
	    else
	    {
	      _LOG.warning("ADFRENDERINGCONTEXT_NOT_AVAILABLE");
	    }
	}
	
	public void encodeScripts(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {
			((ExtendedRenderKitService) _delegate).encodeScripts(context);
			return;
		}
		//There is no encodeScripts because there is 
		/*
	    List<DialogRequest> dialogList = _getDialogList(context, false);
	    boolean hasDialog = ((dialogList != null) && !dialogList.isEmpty());
	    List<String> scriptList = _getScriptList(context, false);
	    boolean hasScript = ((scriptList != null) && !scriptList.isEmpty());

	    if (hasDialog || hasScript)
	    {
	      RenderingContext arc = RenderingContext.getCurrentInstance();
	      if (hasDialog)
	        DialogRequest.addDependencies(context, arc);

	      // =-=AEW How to pick a proper ID?

	      // Write out a script;  let PPR know to use it
	      String scriptId = "::launchScript";
	      PartialPageContext ppContext = arc.getPartialPageContext();
	      // TODO: Create the span with a bogus component where
	      // getClientId() returns the scriptId;  this avoids
	      // the need to downcast - you just need to
	      // call addPartialTarget().  Or, come up with a better
	      // PPR api to make it simpler
	      PartialPageContextImpl ppImpl = (PartialPageContextImpl) ppContext;
	      if (ppImpl != null)
	      {
	        ppImpl.addRenderedPartialTarget(scriptId);
	        ppImpl.pushRenderedPartialTarget(scriptId);
	      }

	      ResponseWriter out = context.getResponseWriter();

	      out.startElement("script", null);
	      out.writeAttribute("id", scriptId, null);

	      XhtmlRenderer.renderScriptDeferAttribute(context, arc);

	      // And render each dialog launch that we need
	      if (hasDialog)
	      {
	        for (DialogRequest dialog : dialogList)
	        {
	          dialog.renderLaunchJavascript(context, arc);
	        }
	      }

	      if (hasScript)
	      {
	        for (String script : scriptList)
	        {
	          out.write(script);
	        }
	      }

	      out.endElement("script");
	      if (hasDialog)
	        dialogList.clear();
	      if (hasScript)
	        scriptList.clear();

	      if (ppImpl != null)
	        ppImpl.popRenderedPartialTarget();
	    }
		*/
	}

	public boolean isStateless(FacesContext context) {
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {			
			return ((ExtendedRenderKitService) _delegate).isStateless(context);
		}		
		// TODO Auto-generated method stub
		return false;
	}

	public boolean shortCircuitRenderView(FacesContext context)
			throws IOException {
		if (ExtendedRenderKitService.class.isAssignableFrom(_delegate
				.getClass())) {			
			return ((ExtendedRenderKitService) _delegate).shortCircuitRenderView(context);
		}

		// TODO Auto-generated method stub
	    if (PartialPageUtils.isPartialRequest(context))
	    {
	      Map<String, Object> requestMap = 
	        context.getExternalContext().getRequestMap();

	      UIViewRoot originalRoot = (UIViewRoot) requestMap.get(
	                         TrinidadPhaseListener.INITIAL_VIEW_ROOT_KEY);
	      // If we're doing a partial update, and the page has changed, switch to a
	      // full page context.
	      if (context.getViewRoot() != originalRoot)
	      {
	        ViewHandler vh = context.getApplication().getViewHandler();

	        String viewId = context.getViewRoot().getViewId();
	        String redirect = vh.getActionURL(context, viewId);
	        context.getExternalContext().redirect(redirect);
	        if (_LOG.isFine())
	        {
	          _LOG.fine("Page navigation to {0} happened during a PPR request " +
	                    "on {1};  Apache Trinidad is forcing a redirect.",
	                    new String[]{viewId, originalRoot.getViewId()});
	        }

	        return true;
	      }
	    }
		return false;
	}

	static private final TrinidadLogger _LOG =
		     TrinidadLogger.createTrinidadLogger(SkinRenderKit.class);
	
	//END METHODS INHERITED FROM ExtendedRenderKitService
}
