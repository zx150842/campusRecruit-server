package com.bupt.pcncad.util;

import net.sf.json.util.PropertyFilter;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhang
 * Date: 13-9-2
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class IgnoreFieldProcessorImpl implements PropertyFilter {

    private String[] fields;
    private boolean ignoreColl = false;

    public IgnoreFieldProcessorImpl(){

    }

    public IgnoreFieldProcessorImpl(String[] fields){
        this.fields = fields;
    }

    public IgnoreFieldProcessorImpl(boolean ignoreColl, String[] fields){
        this.fields = fields;
        this.ignoreColl = ignoreColl;
    }

    public IgnoreFieldProcessorImpl(boolean ignoreColl){
        this.ignoreColl = ignoreColl;
    }

    @Override
    public boolean apply(Object source, String name, Object value) {
        Field  declaredField = null;
        if(value == null)
            return true;
        if(!"data".equals(name) && "data" != name && !"totalSize".equals(name) && "totalSize" != name){
            try{
                declaredField = source.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e){
                e.printStackTrace();
            }
        }
        if(declaredField != null){
            if(ignoreColl){
                if(declaredField.getType() == Collection.class || declaredField.getType() == Set.class){
                    return true;
                }
            }
        }
        if(fields != null && fields.length > 0){
            if(juge(fields, name)){
                return true;
            }
            else
                return false;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean juge(String[] s, String s2){
        boolean b = false;
        for(String sl : s){
            if(s2.equals(sl))
                b = true;
        }
        return b;
    }

    public String[] getFields(){
        return fields;
    }

    public void setFields(String[] fields){
        this.fields = fields;
    }

    public boolean isIgnoreColl(){
        return ignoreColl;
    }

    public void setIgnoreColl(boolean ignoreColl){
        this.ignoreColl = ignoreColl;
    }
}
