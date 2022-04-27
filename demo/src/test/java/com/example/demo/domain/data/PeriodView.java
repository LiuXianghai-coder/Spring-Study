package com.example.demo.domain.data;

/**
 * @author xhliu
 * @create 2022-04-27-16:27
 **/
class PeriodView {
    private String id;

    private String projId;

    private Boolean isAdd;

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

    public Boolean getAdd() {
        return isAdd;
    }

    public void setAdd(Boolean add) {
        isAdd = add;
    }

    static PeriodView EXAMPLE_ONE = new Builder().withId("0x3f3f3f3f")
            .withIsAdd(false)
            .withProjId("eaa8a53")
            .build();

    static PeriodView EXAMPLE_TWO = new Builder().withId("b5bccee")
            .withProjId("23b8348")
            .build();

    static PeriodView EXAMPLE_THREE = new Builder().withId("afec75e")
            .withIsAdd(true)
            .withProjId("d274838")
            .build();

    static PeriodView EXAMPLE_FOUR = new Builder().withId("f053fd0")
            .withIsAdd(true)
            .withProjId("a2b8e13")
            .build();

    static PeriodView EXAMPLE_FIVE = new Builder().withId("d274838")
            .withIsAdd(true)
            .withProjId("87349b1")
            .build();

    static PeriodView EXAMPLE_SIX = new Builder().withId("ab41ea8")
            .withIsAdd(Boolean.FALSE)
            .withProjId("572c716")
            .build();

    public static final class Builder {
        private String id;
        private String projId;
        private Boolean isAdd;

        private Builder() {
        }

        public static Builder aPeriodView() {
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

        public Builder withIsAdd(Boolean isAdd) {
            this.isAdd = isAdd;
            return this;
        }

        public PeriodView build() {
            PeriodView periodView = new PeriodView();
            periodView.setId(id);
            periodView.setProjId(projId);
            periodView.isAdd = this.isAdd;
            return periodView;
        }
    }
}
