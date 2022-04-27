package com.example.demo.domain.data;

import java.util.Arrays;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-04-27-16:25
 **/
class ProductView {
    private String id;

    private String projId;

    private String name;

    private boolean isPeriod;

    private List<PeriodView> periodViewList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPeriod() {
        return isPeriod;
    }

    public void setPeriod(boolean period) {
        isPeriod = period;
    }

    public List<PeriodView> getPeriodViewList() {
        return periodViewList;
    }

    public void setPeriodViewList(List<PeriodView> periodViewList) {
        this.periodViewList = periodViewList;
    }

    public static ProductView EXAMPLE_ONE = new Builder()
            .withName("Example 1")
            .withIsPeriod(false)
            .withProjId("7da8f4e")
            .withPeriodViewList(Arrays.asList(
                    PeriodView.EXAMPLE_ONE,
                    PeriodView.EXAMPLE_TWO,
                    PeriodView.EXAMPLE_THREE)
            ).build();

    public static ProductView EXAMPLE_TWO = new Builder()
            .withName("Example 2")
            .withIsPeriod(true)
            .withProjId("c525286")
            .withPeriodViewList(Arrays.asList(
                    PeriodView.EXAMPLE_FIVE,
                    PeriodView.EXAMPLE_TWO,
                    PeriodView.EXAMPLE_FOUR)
            ).build();

    public static ProductView EXAMPLE_THREE = new Builder()
            .withName("Example 3")
            .withIsPeriod(true)
            .withProjId("ee78b61")
            .withPeriodViewList(Arrays.asList(
                    PeriodView.EXAMPLE_SIX,
                    PeriodView.EXAMPLE_TWO,
                    PeriodView.EXAMPLE_FIVE)
            ).build();

    public static final class Builder {
        private String id;
        private String projId;
        private String name;
        private boolean isPeriod;
        private List<PeriodView> periodViewList;

        private Builder() {
        }

        public static Builder aProductView() {
            return new Builder();
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withProjId(String projId) {
            this.projId = projId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIsPeriod(boolean isPeriod) {
            this.isPeriod = isPeriod;
            return this;
        }

        public Builder withPeriodViewList(List<PeriodView> periodViewList) {
            this.periodViewList = periodViewList;
            return this;
        }

        public ProductView build() {
            ProductView productView = new ProductView();
            productView.setId(id);
            productView.setProjId(projId);
            productView.setName(name);
            productView.setPeriodViewList(periodViewList);
            productView.isPeriod = this.isPeriod;
            return productView;
        }
    }
}
