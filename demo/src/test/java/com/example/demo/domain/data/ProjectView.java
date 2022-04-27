package com.example.demo.domain.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-04-27-16:21
 **/
public class ProjectView {
    private String projId;

    private Project proj;

    private List<ProductView> productViewList;

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public Project getProj() {
        return proj;
    }

    public void setProj(Project proj) {
        this.proj = proj;
    }

    public List<ProductView> getProductViewList() {
        return productViewList;
    }

    public void setProductViewList(List<ProductView> productViewList) {
        this.productViewList = productViewList;
    }

    public static ProjectView EXAMPLE_ONE = new Builder()
            .withProductViewList(Arrays.asList(ProductView.EXAMPLE_ONE, ProductView.EXAMPLE_TWO))
            .withProj(Project.EXAMPLE_ONE)
            .withProjId("0000000")
            .build();

    public static ProjectView EXAMPLE_TWO = new Builder()
            .withProductViewList(Arrays.asList(ProductView.EXAMPLE_THREE, ProductView.EXAMPLE_TWO))
            .withProj(Project.EXAMPLE_TWO)
            .withProjId("0000010")
            .build();

    public static ProjectView EXAMPLE_THREE = new Builder()
            .withProductViewList(Arrays.asList(ProductView.EXAMPLE_THREE, ProductView.EXAMPLE_ONE))
            .withProj(Project.EXAMPLE_THREE)
            .withProjId("0000899")
            .build();

    public static final class Builder {
        private String projId;
        private Project proj;
        private List<ProductView> productViewList;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withProjId(String projId) {
            this.projId = projId;
            return this;
        }

        public Builder withProj(Project proj) {
            this.proj = proj;
            return this;
        }

        public Builder withProductViewList(List<ProductView> productViewList) {
            this.productViewList = productViewList;
            return this;
        }

        public ProjectView build() {
            ProjectView projectView = new ProjectView();
            projectView.setProjId(projId);
            projectView.setProj(proj);
            projectView.setProductViewList(productViewList);
            return projectView;
        }
    }
}
