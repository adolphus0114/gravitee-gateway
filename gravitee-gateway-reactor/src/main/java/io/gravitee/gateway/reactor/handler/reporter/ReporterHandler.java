/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.reactor.handler.reporter;

import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.api.Response;
import io.gravitee.gateway.api.handler.Handler;
import io.gravitee.gateway.report.ReporterService;

/**
 * @author David BRASSELY (david at gravitee.io)
 * @author GraviteeSource Team
 */
public class ReporterHandler implements Handler<Response> {

    private final ReporterService reporterService;
    private final Handler<Response> wrappedHandler;
    private final Request request;

    public ReporterHandler(ReporterService reporterService, final Request request, Handler<Response> wrappedHandler) {
        this.reporterService = reporterService;
        this.request = request;
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public void handle(Response result) {
        // Report and handle result to the initial wrapped handler
        wrappedHandler.handle(result);

        reporterService.report(request.metrics());
    }
}