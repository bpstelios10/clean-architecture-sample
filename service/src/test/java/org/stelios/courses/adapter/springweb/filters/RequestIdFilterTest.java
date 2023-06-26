package org.stelios.courses.adapter.springweb.filters;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith(MockitoExtension.class)
class RequestIdFilterTest {

    private static final String REQUEST_ID_HEADER = "RequestId";

    private RequestIdFilter requestIdFilter;
    @Mock
    private FilterChain chain;

    @BeforeEach
    void beforeEach() {
        MDC.clear();
        requestIdFilter = new RequestIdFilter();
    }

    @AfterAll
    static void afterAll() {
        MDC.clear();
    }

    @Test
    void doFilter_addsValidUuidAsRequestIdHeaderInResponseAndMDC_whenRequestNotHaveIt() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isNotNull();
        String requestIdHeaderValue = Objects.requireNonNull(response.getHeaderValue(REQUEST_ID_HEADER)).toString();
        assertDoesNotThrow(() -> UUID.fromString(requestIdHeaderValue).toString());
        assertThat(MDC.get(REQUEST_ID_HEADER)).isEqualTo(requestIdHeaderValue);
    }

    @Test
    void doFilter_addsSameRequestIdHeaderInResponseAndMDC_whenRequestHasValidOne() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        UUID requestIdValue = UUID.randomUUID();
        request1.addHeader(REQUEST_ID_HEADER, requestIdValue);
        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isEqualTo(requestIdValue.toString());
        assertThat(MDC.get(REQUEST_ID_HEADER)).isEqualTo(requestIdValue.toString());
    }

    @Test
    void doFilter_addsNewValidUuidAsRequestIdHeaderInResponse_whenRequestHasInvalidOne() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.addHeader(REQUEST_ID_HEADER, "random");
        MockHttpServletResponse response = new MockHttpServletResponse();
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        assertThat(response.getHeaderValue(REQUEST_ID_HEADER)).isNotEqualTo("random");
        String requestIdHeaderValue = Objects.requireNonNull(response.getHeaderValue(REQUEST_ID_HEADER)).toString();
        assertDoesNotThrow(() -> UUID.fromString(requestIdHeaderValue).toString());
    }

    @Test
    void doFilter_setsContentTypeHeaderTo_whenResponseHasErrorCode() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(500);
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        Object requestIdHeaderValue = response.getHeaderValue(REQUEST_ID_HEADER);
        assertThat(requestIdHeaderValue).isNotNull();
        assertThat(MDC.get(REQUEST_ID_HEADER)).isEqualTo(requestIdHeaderValue);
        assertThat(response.getHeaderValue(CONTENT_TYPE)).isEqualTo(APPLICATION_JSON_VALUE);
    }

    @Test
    void doFilter_doesNotSetContentTypeHeaderTo_whenResponseHasNonErrorCode() throws ServletException, IOException {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(200);
        doNothing().when(chain).doFilter(request1, response);

        requestIdFilter.doFilter(request1, response, chain);

        Object requestIdHeaderValue = response.getHeaderValue(REQUEST_ID_HEADER);
        assertThat(requestIdHeaderValue).isNotNull();
        assertThat(MDC.get(REQUEST_ID_HEADER)).isEqualTo(requestIdHeaderValue);
        assertThat(response.getHeaderValue(CONTENT_TYPE)).isNull();
    }

    @Test
    void getHeaderValue_returnsRequestedHeader() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("requestedHeader", "value");
        request.addHeader("other-header", "other-value");

        String response = requestIdFilter.getHeaderValue(request, "requestedHeader");

        assertThat(response).isEqualTo("value");
    }

    @Test
    void getHeaderValue_returnsRequestedHeader_caseInsensitive() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("requestedHeader", "value");
        request.addHeader("other-header", "other-value");

        String response = requestIdFilter.getHeaderValue(request, "requestedheader");

        assertThat(response).isEqualTo("value");
    }
}
