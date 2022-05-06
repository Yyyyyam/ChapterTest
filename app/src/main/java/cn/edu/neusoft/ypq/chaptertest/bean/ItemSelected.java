package cn.edu.neusoft.ypq.chaptertest.bean;

import java.io.Serializable;

/**
 * 作者:颜培琦
 * 时间:2022/4/25
 * 功能:ItemSelected
 */
public class ItemSelected implements Serializable {
    private  String name;
    private  Integer position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ItemSelected{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}
