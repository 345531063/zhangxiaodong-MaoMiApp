package cn.com.yibin.maomi.core.web.controller;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.UrlPathHelper;


@Controller(value="urlFilenameViewController")
public final class UrlFilenameViewController 
{
	/**
	 * Return the name of the view to render for this request, based on the
	 * given lookup path. Called by {@link #handleRequestInternal}.
	 * @param request current HTTP request
	 * @return a view name for this request (never <code>null</code>)
	 * @see #handleRequestInternal
	 * @see #setAlwaysUseFullPath
	 * @see #setUrlDecode
	 */
	private String prefix = "";

	private String suffix = "";
	
	private String redirectPageUrl = "/WEB-INF/views";
	/**
	 * Retrieves the URL path to use for lookup and delegates to
	 * {@link #getViewNameForRequest}. Also adds the content of 
	 * {@link RequestContextUtils#getInputFlashMap} to the model.
	 */
	@RequestMapping(value="/**/*.ap")
	public ModelAndView getViewNameByAp(HttpServletRequest request, HttpServletResponse response) {
//		String lookupPath = getUrlPathHelper().getLookupPathForRequest(request);
		String viewName = getViewNameForRequest(request);
//		LogUtil.debug("Returning view name '" + viewName + "' for lookup path [" + lookupPath + "]");
//		Map<String,?> modelData = RequestContextUtils.getInputFlashMap(request);
//		this.setRequestAttributesByMap(request, modelData);
		try {
			Map<String,String[]> modelData      = request.getParameterMap();
			for(String key:modelData.keySet()){
				request.setAttribute(key, modelData.get(key)[0].toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  new ModelAndView(redirectPageUrl+"/"+viewName);
	}
	
	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	/**
	 * Set if URL lookup should always use full path within current servlet
	 * context. Else, the path within the current servlet mapping is used
	 * if applicable (i.e. in the case of a ".../*" servlet mapping in web.xml).
	 * Default is "false".
	 * @see UrlPathHelper#setAlwaysUseFullPath
	 */
	public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
		this.urlPathHelper.setAlwaysUseFullPath(alwaysUseFullPath);
	}

	/**
	 * Set if context path and request URI should be URL-decoded.
	 * Both are returned <i>undecoded</i> by the Servlet API,
	 * in contrast to the servlet path.
	 * <p>Uses either the request encoding or the default encoding according
	 * to the Servlet spec (ISO-8859-1).
	 * @see UrlPathHelper#setUrlDecode
	 */
	public void setUrlDecode(boolean urlDecode) {
		this.urlPathHelper.setUrlDecode(urlDecode);
	}

	/**
	 * Set the UrlPathHelper to use for the resolution of lookup paths.
	 * <p>Use this to override the default UrlPathHelper with a custom subclass,
	 * or to share common UrlPathHelper settings across multiple MethodNameResolvers
	 * and HandlerMappings.
	 * @see org.springframework.web.servlet.handler.AbstractUrlHandlerMapping#setUrlPathHelper
	 */
	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}

	/**
	 * Return the UrlPathHelper to use for the resolution of lookup paths.
	 */
	protected UrlPathHelper getUrlPathHelper() {
		return this.urlPathHelper;
	}



	/** Request URL path String --> view name String */
	private final Map<String, String> viewNameCache = new ConcurrentHashMap<String, String>();


	/**
	 * Set the prefix to prepend to the request URL filename
	 * to build a view name.
	 */
	public void setPrefix(String prefix) {
		this.prefix = (prefix != null ? prefix : "");
	}

	/**
	 * Return the prefix to prepend to the request URL filename.
	 */
	protected String getPrefix() {
		return this.prefix;
	}

	/**
	 * Set the suffix to append to the request URL filename
	 * to build a view name.
	 */
	public void setSuffix(String suffix) {
		this.suffix = (suffix != null ? suffix : "");
	}

	/**
	 * Return the suffix to append to the request URL filename.
	 */
	protected String getSuffix() {
		return this.suffix;
	}


	/**
	 * Returns view name based on the URL filename,
	 * with prefix/suffix applied when appropriate.
	 * @see #extractViewNameFromUrlPath
	 * @see #setPrefix
	 * @see #setSuffix
	 */
	protected String getViewNameForRequest(HttpServletRequest request) {
		String uri = extractOperableUrl(request);
		return getViewNameForUrlPath(uri);
	}

	/**
	 * Extract a URL path from the given request,
	 * suitable for view name extraction.
	 * @param request current HTTP request
	 * @return the URL to use for view name extraction
	 */
	protected String extractOperableUrl(HttpServletRequest request) {
		String urlPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (!StringUtils.hasText(urlPath)) {
			urlPath = getUrlPathHelper().getLookupPathForRequest(request);
		}
		return urlPath;
	}

	/**
	 * Returns view name based on the URL filename,
	 * with prefix/suffix applied when appropriate.
	 * @param uri the request URI; for example <code>"/index.html"</code>
	 * @return the extracted URI filename; for example <code>"index"</code>
	 * @see #extractViewNameFromUrlPath
	 * @see #postProcessViewName
	 */
	protected String getViewNameForUrlPath(String uri) {
		String viewName = this.viewNameCache.get(uri);
		if (viewName == null) {
			viewName = extractViewNameFromUrlPath(uri);
			viewName = postProcessViewName(viewName);
			this.viewNameCache.put(uri, viewName);
		}
		return viewName;
	}

	/**
	 * Extract the URL filename from the given request URI.
	 * @param uri the request URI; for example <code>"/index.html"</code>
	 * @return the extracted URI filename; for example <code>"index"</code>
	 */
	protected String extractViewNameFromUrlPath(String uri) {
		int start = (uri.charAt(0) == '/' ? 1 : 0);
		int lastIndex = uri.lastIndexOf(".");
		int end = (lastIndex < 0 ? uri.length() : lastIndex);
		return uri.substring(start, end);
	}

	/**
	 * Build the full view name based on the given view name
	 * as indicated by the URL path.
	 * <p>The default implementation simply applies prefix and suffix.
	 * This can be overridden, for example, to manipulate upper case
	 * / lower case, etc.
	 * @param viewName the original view name, as indicated by the URL path
	 * @return the full view name to use
	 * @see #getPrefix()
	 * @see #getSuffix()
	 */
	protected String postProcessViewName(String viewName) {
		return getPrefix() + viewName + getSuffix();
	}
	//protected abstract String getViewNameForRequest(HttpServletRequest request);
}
