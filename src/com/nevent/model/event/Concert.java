package com.nevent.model.event;

import com.nevent.model.performer.Performer;

public class Concert extends Event {
    Performer opener;
    Performer mainAct;
    Integer performanceTimeOpener;
    Integer performanceTimeMainAct;

    public Integer getPerformanceTimeOpener() {
        return performanceTimeOpener;
    }

    public void setPerformanceTimeOpener(Integer performanceTimeOpener) {
        this.performanceTimeOpener = performanceTimeOpener;
    }

    public Integer getPerformanceTimeMainAct() {
        return performanceTimeMainAct;
    }

    public void setPerformanceTimeMainAct(Integer performanceTimeMainAct) {
        this.performanceTimeMainAct = performanceTimeMainAct;
    }

    public Performer getOpener() {
        return opener;
    }

    public void setOpener(Performer opener) {
        this.opener = opener;
    }

    public Performer getMainAct() {
        return mainAct;
    }

    public void setMainAct(Performer mainAct) {
        this.mainAct = mainAct;
    }
}
