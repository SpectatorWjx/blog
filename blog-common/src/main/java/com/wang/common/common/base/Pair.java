package com.wang.common.common.base;

import lombok.*;

import java.io.Serializable;

/***
 @author:wjx zhijiu
 @date:2020-040-3
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public final class Pair<S, T> implements Serializable{

    @Getter
    private S first;
    @Getter
    private T second;

    public static <S, T> Pair<S, T> of(S first, T second) {
        return new Pair<S, T>(first, second);
    }


    @Override
    public boolean equals(Object object){
        if(!(object instanceof Pair)){
            return false;
        }
        if(null==this.getFirst()||null==this.getSecond()){
            throw new IllegalStateException("ele can not be null");
        }
        if(null==((Pair) object).getFirst()||null==((Pair) object).getSecond()){
            throw new IllegalStateException("ele can not be null");
        }
        if(!this.getFirst().equals(((Pair) object).getFirst())){
            return false;
        }
        if(!this.getSecond().equals(((Pair) object).getSecond())){
            return false;
        }
        return true;
    }

}