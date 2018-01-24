/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.web.firewall;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.fail;

/**
 * @author Rob Winch
 */
public class StrictHttpFirewallTests {
	public String[] unnormalizedPaths = { "/..", "/./path/", "/path/path/.", "/path/path//.", "./path/../path//.",
			"./path", ".//path", ".", "/path//" };

	private StrictHttpFirewall firewall = new StrictHttpFirewall();

	private MockHttpServletRequest request = new MockHttpServletRequest();

	@Test
	public void getFirewalledRequestWhenRequestURINotNormalizedThenThrowsRequestRejectedException() throws Exception {
		for (String path : this.unnormalizedPaths) {
			this.request = new MockHttpServletRequest();
			this.request.setRequestURI(path);
			try {
				this.firewall.getFirewalledRequest(this.request);
				fail(path + " is un-normalized");
			} catch (RequestRejectedException expected) {
			}
		}
	}

	@Test
	public void getFirewalledRequestWhenContextPathNotNormalizedThenThrowsRequestRejectedException() throws Exception {
		for (String path : this.unnormalizedPaths) {
			this.request = new MockHttpServletRequest();
			this.request.setContextPath(path);
			try {
				this.firewall.getFirewalledRequest(this.request);
				fail(path + " is un-normalized");
			} catch (RequestRejectedException expected) {
			}
		}
	}

	@Test
	public void getFirewalledRequestWhenServletPathNotNormalizedThenThrowsRequestRejectedException() throws Exception {
		for (String path : this.unnormalizedPaths) {
			this.request = new MockHttpServletRequest();
			this.request.setServletPath(path);
			try {
				this.firewall.getFirewalledRequest(this.request);
				fail(path + " is un-normalized");
			} catch (RequestRejectedException expected) {
			}
		}
	}

	@Test
	public void getFirewalledRequestWhenPathInfoNotNormalizedThenThrowsRequestRejectedException() throws Exception {
		for (String path : this.unnormalizedPaths) {
			this.request = new MockHttpServletRequest();
			this.request.setPathInfo(path);
			try {
				this.firewall.getFirewalledRequest(this.request);
				fail(path + " is un-normalized");
			} catch (RequestRejectedException expected) {
			}
		}
	}

	// --- ; ---

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenSemicolonInContextPathThenThrowsRequestRejectedException() {
		this.request.setContextPath(";/context");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenSemicolonInServletPathThenThrowsRequestRejectedException() {
		this.request.setServletPath("/spring;/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenSemicolonInPathInfoThenThrowsRequestRejectedException() {
		this.request.setPathInfo("/path;/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenSemicolonInRequestUriThenThrowsRequestRejectedException() {
		this.request.setRequestURI("/path;/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenEncodedSemicolonInContextPathThenThrowsRequestRejectedException() {
		this.request.setContextPath("%3B/context");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenEncodedSemicolonInServletPathThenThrowsRequestRejectedException() {
		this.request.setServletPath("/spring%3B/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenEncodedSemicolonInPathInfoThenThrowsRequestRejectedException() {
		this.request.setPathInfo("/path%3B/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenEncodedSemicolonInRequestUriThenThrowsRequestRejectedException() {
		this.request.setRequestURI("/path%3B/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInContextPathThenThrowsRequestRejectedException() {
		this.request.setContextPath("%3b/context");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInServletPathThenThrowsRequestRejectedException() {
		this.request.setServletPath("/spring%3b/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInPathInfoThenThrowsRequestRejectedException() {
		this.request.setPathInfo("/path%3b/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInRequestUriThenThrowsRequestRejectedException() {
		this.request.setRequestURI("/path%3b/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenSemicolonInContextPathAndAllowSemicolonThenNoException() {
		this.firewall.setAllowSemicolon(true);
		this.request.setContextPath(";/context");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenSemicolonInServletPathAndAllowSemicolonThenNoException() {
		this.firewall.setAllowSemicolon(true);
		this.request.setServletPath("/spring;/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenSemicolonInPathInfoAndAllowSemicolonThenNoException() {
		this.firewall.setAllowSemicolon(true);
		this.request.setPathInfo("/path;/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenSemicolonInRequestUriAndAllowSemicolonThenNoException() {
		this.firewall.setAllowSemicolon(true);
		this.request.setRequestURI("/path;/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenEncodedSemicolonInContextPathAndAllowSemicolonThenNoException() {
		this.firewall.setAllowUrlEncodedPercent(true);
		this.firewall.setAllowSemicolon(true);
		this.request.setContextPath("%3B/context");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenEncodedSemicolonInServletPathAndAllowSemicolonThenNoException() {
		this.firewall.setAllowUrlEncodedPercent(true);
		this.firewall.setAllowSemicolon(true);
		this.request.setServletPath("/spring%3B/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenEncodedSemicolonInPathInfoAndAllowSemicolonThenNoException() {
		this.firewall.setAllowUrlEncodedPercent(true);
		this.firewall.setAllowSemicolon(true);
		this.request.setPathInfo("/path%3B/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenEncodedSemicolonInRequestUriAndAllowSemicolonThenNoException() {
		this.firewall.setAllowSemicolon(true);
		this.request.setRequestURI("/path%3B/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInContextPathAndAllowSemicolonThenNoException() {
		this.firewall.setAllowUrlEncodedPercent(true);
		this.firewall.setAllowSemicolon(true);
		this.request.setContextPath("%3b/context");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInServletPathAndAllowSemicolonThenNoException() {
		this.firewall.setAllowUrlEncodedPercent(true);
		this.firewall.setAllowSemicolon(true);
		this.request.setServletPath("/spring%3b/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInPathInfoAndAllowSemicolonThenNoException() {
		this.firewall.setAllowUrlEncodedPercent(true);
		this.firewall.setAllowSemicolon(true);
		this.request.setPathInfo("/path%3b/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenLowercaseEncodedSemicolonInRequestUriAndAllowSemicolonThenNoException() {
		this.firewall.setAllowSemicolon(true);
		this.request.setRequestURI("/path%3b/");

		this.firewall.getFirewalledRequest(this.request);
	}

	// --- encoded . ---

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenEncodedPeriodInThenThrowsRequestRejectedException() {
		this.request.setRequestURI("/%2E/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenLowercaseEncodedPeriodInThenThrowsRequestRejectedException() {
		this.request.setRequestURI("/%2e/");

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenAllowEncodedPeriodAndEncodedPeriodInThenNoException() {
		this.firewall.setAllowUrlEncodedPeriod(true);
		this.request.setRequestURI("/%2E/");

		this.firewall.getFirewalledRequest(this.request);
	}

	// --- from DefaultHttpFirewallTests ---

	/**
	 * On WebSphere 8.5 a URL like /context-root/a/b;%2f1/c can bypass a rule on
	 * /a/b/c because the pathInfo is /a/b;/1/c which ends up being /a/b/1/c
	 * while Spring MVC will strip the ; content from requestURI before the path
	 * is URL decoded.
	 */
	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenLowercaseEncodedPathThenException() {
		this.request.setRequestURI("/context-root/a/b;%2f1/c");
		this.request.setContextPath("/context-root");
		this.request.setServletPath("");
		this.request.setPathInfo("/a/b;/1/c"); // URL decoded requestURI
		this.firewall.getFirewalledRequest(this.request);
	}

	@Test(expected = RequestRejectedException.class)
	public void getFirewalledRequestWhenUppercaseEncodedPathThenException() {
		this.request.setRequestURI("/context-root/a/b;%2F1/c");
		this.request.setContextPath("/context-root");
		this.request.setServletPath("");
		this.request.setPathInfo("/a/b;/1/c"); // URL decoded requestURI

		this.firewall.getFirewalledRequest(this.request);
	}

	@Test
	public void getFirewalledRequestWhenAllowUrlEncodedSlashAndLowercaseEncodedPathThenNoException() {
		this.firewall.setAllowUrlEncodedSlash(true);
		this.firewall.setAllowSemicolon(true);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/context-root/a/b;%2f1/c");
		request.setContextPath("/context-root");
		request.setServletPath("");
		request.setPathInfo("/a/b;/1/c"); // URL decoded requestURI

		this.firewall.getFirewalledRequest(request);
	}

	@Test
	public void getFirewalledRequestWhenAllowUrlEncodedSlashAndUppercaseEncodedPathThenNoException() {
		this.firewall.setAllowUrlEncodedSlash(true);
		this.firewall.setAllowSemicolon(true);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/context-root/a/b;%2F1/c");
		request.setContextPath("/context-root");
		request.setServletPath("");
		request.setPathInfo("/a/b;/1/c"); // URL decoded requestURI

		this.firewall.getFirewalledRequest(request);
	}
}