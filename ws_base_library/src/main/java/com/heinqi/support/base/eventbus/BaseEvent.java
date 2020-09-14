package com.heinqi.support.base.eventbus;

/**
 * 基础EventBus
 *
 * @author wg
 * @time 2017/5/25 10:12
 */
public class BaseEvent<T> {
    public int mType;
    public String name;
    public BaseEvent(String name) {
        this.name = name;
    }
    public BaseEvent(int mType) {
        this.mType = mType;
    }
    public BaseEvent(String name, int mType) {
        this.name = name;
        this.mType = mType;
    }
    public BaseEvent() {

    }
    public BaseEvent(String name, T mContent) {
        this.name=name;
        this.mContent = mContent;
    }

    public BaseEvent(String name, int mType, T mContent) {
        this.name=name;
        this.mType=mType;
        this.mContent = mContent;
    }


    public T mContent;


}
