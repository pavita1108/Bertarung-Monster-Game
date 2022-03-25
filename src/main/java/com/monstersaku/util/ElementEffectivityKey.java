package com.monstersaku.util;

public class ElementEffectivityKey {
    private ElementType source;
    private ElementType target;

    public ElementEffectivityKey(ElementType source, ElementType target){
        this.source = source;
        this.target = target;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementEffectivityKey) {
            ElementEffectivityKey s = (ElementEffectivityKey)obj;
            return source == s.source && target == s.target;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + source.name().hashCode();
        result = result * prime + target.name().hashCode();
        return result;
    }

}


