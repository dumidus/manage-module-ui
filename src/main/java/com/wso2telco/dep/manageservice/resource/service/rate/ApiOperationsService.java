package com.wso2telco.dep.manageservice.resource.service.rate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wso2telco.dep.manageservice.resource.model.Callback;
import com.wso2telco.dep.manageservice.resource.model.rate.ApiOperation;
import com.wso2telco.dep.manageservice.resource.resource.RequestTransferable;
import com.wso2telco.dep.manageservice.resource.service.AbstractService;
import com.wso2telco.dep.manageservice.resource.util.Messages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Copyright (c) 2016, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 * <p>
 * WSO2.Telco Inc. licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ApiOperationsService extends AbstractService {

    private HttpClient client;
    private ObjectMapper mapper;
    private final Log log = LogFactory.getLog(CategoryService.class);

    public ApiOperationsService() {
        this.client = HttpClientBuilder.create().build();
        this.mapper = new ObjectMapper();
    }

    @Override
    public Callback executeGet(String authenticationCredential) {
        return null;
    }

    @Override
    public Callback executeGet(String authenticationCredential, List<String> pathParamStringList) {
        HttpGet httpGet = new HttpGet(new StringBuilder("http://localhost:9763/ratecard-service/ratecardservice/").append("apis/" + pathParamStringList.get(0) + "/operations").toString());

        httpGet.addHeader("Authorization", authenticationCredential);

        try {
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                ApiOperation[] apiOperations = mapper.readValue(response.getEntity().getContent(), ApiOperation[].class);
                return new Callback().setPayload(apiOperations).setSuccess(true).setMessage("Api Operations Loaded Successfully");
            } else {
                log.error(response.getStatusLine().getStatusCode() + " Error loading api operations from hub");
                return new Callback().setPayload(null).setSuccess(false).setMessage(Messages.API_OPERATIONS_LOADING_EROOR.getValue());
            }

        } catch (IOException e) {
            log.error(" Exception while loading categories from hub " + e);
            return new Callback().setPayload(null).setSuccess(false).setMessage(Messages.API_OPERATIONS_LOADING_EROOR.getValue());
        }
    }

    @Override
    public Callback executePost(RequestTransferable request, String authenticationCredential) {
        return null;
    }
}
