package com.example.FlowFree.objects.xmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristinn on 19.9.2014.
 */
public class Flow {

    private final int LENGTHOFTOTAL = 9;
    private final int NUMBEROFLOWS = 6;
    private int Fid;
    private int size;
    private String flow1;
    private String flow2;
    private String flow3;
    private String flow4;
    private String flow5;
    private String flow6;

    public Flow(String size, String inFlows)
    {
        List<String> flows = new ArrayList<String>();
        String f;
        char[] flowArray = inFlows.toCharArray();
        StringBuilder builder;
        int offset = 0;

        for(int p = 0; p < NUMBEROFLOWS; p++)
        {
            f = "uninitialized";
            builder = new StringBuilder();

            for(int i = 0; i < LENGTHOFTOTAL; i++)
            {
                if(p * LENGTHOFTOTAL + i + offset >= flowArray.length)
                {
                    f = "";
                }
                else
                {
                    builder.append(flowArray[p * LENGTHOFTOTAL + i + offset]);
                }
            }

            if(!(f.equals("")))
            {
                f = builder.toString();
            }
            offset = offset + 2;
            flows.add(f);
        }

        this.flow1 = flows.get(0);
        this.flow2 = flows.get(1);
        this.flow3 = flows.get(2);
        this.flow4 = flows.get(3);
        this.flow5 = flows.get(4);
        this.flow6 = flows.get(5);
        this.size = Character.getNumericValue(size.toCharArray()[0]);

    }

    public int getFid() {
        return Fid;
    }

    public void setFid(int fid) {
        Fid = fid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFlow1() {
        return flow1;
    }

    public void setFlow1(String flow1) {
        this.flow1 = flow1;
    }

    public String getFlow2() {
        return flow2;
    }

    public void setFlow2(String flow2) {
        this.flow2 = flow2;
    }

    public String getFlow3() {
        return flow3;
    }

    public void setFlow3(String flow3) {
        this.flow3 = flow3;
    }

    public String getFlow4() {
        return flow4;
    }

    public void setFlow4(String flow4) {
        this.flow4 = flow4;
    }

    public String getFlow5() {
        return flow5;
    }

    public void setFlow5(String flow5) {
        this.flow5 = flow5;
    }

    public String getFlow6() {
        return flow6;
    }

    public void setFlow6(String flow6) {
        this.flow6 = flow6;
    }
}
