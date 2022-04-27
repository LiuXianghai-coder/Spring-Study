package com.example.demo.domain;


import com.example.demo.domain.data.ProjectView;
import com.example.demo.domain.meta.Response;

/**
 * @author xhliu
 * @create 2022-04-27-16:35
 **/
public class Data {
    private Response response;

    private ProjectView project;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ProjectView getProjectView() {
        return project;
    }

    public void setProjectView(ProjectView project) {
        this.project = project;
    }

    public static Data EXAMPLE_ONE = new Builder().withProject(ProjectView.EXAMPLE_ONE)
            .withResponse(Response.OK).build();

    public static Data EXAMPLE_TWO = new Builder().withProject(ProjectView.EXAMPLE_TWO)
            .withResponse(Response.OK).build();

    public static Data EXAMPLE_THREE = new Builder().withProject(ProjectView.EXAMPLE_THREE)
            .withResponse(Response.FAILED).build();

    public static final class Builder {
        private Response response;
        private ProjectView project;

        private Builder() {
        }

        public static Builder aData() {
            return new Builder();
        }

        public Builder withResponse(Response response) {
            this.response = response;
            return this;
        }

        public Builder withProject(ProjectView project) {
            this.project = project;
            return this;
        }

        public Data build() {
            Data data = new Data();
            data.setResponse(response);
            data.project = this.project;
            return data;
        }
    }
}
