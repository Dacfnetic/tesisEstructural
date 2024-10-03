/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author Diego
 */
public class Story {
    
    public String id;
    public String name;
    public String category;
    public float lowerStoryHeight;
    public float upperStoryHeight;
    public float storyHeight;
    
    
    public Story(){
        
    }
    
    public void setParams(String id, String name, String category, float lowerStoryHeight, float storyHeight){
        this.id = id;
        this.name = name;
        this.category = category;
        this.lowerStoryHeight = lowerStoryHeight;
        this.storyHeight = storyHeight;
        calculateUpperStoryHeight();
    }
    
    public void setParams(List data){
        
        Object[] story = data.toArray();
        String id2 = story[0].toString();
        String name2 = story[1].toString();
        String category2 = story[2].toString();
        String sH = story[5].toString();
        float storyHeight2 = Float.parseFloat(sH);
        
        String lSH = story[3].toString();
        float lowerStoryHeight2 = Float.parseFloat(lSH);
        
        this.id = id2;
        this.name = name2;
        this.category = category2;
        this.lowerStoryHeight = lowerStoryHeight2;
        this.storyHeight = storyHeight2;
        calculateUpperStoryHeight();
    }
    
    
    
    
    public void calculateUpperStoryHeight(){
        this.upperStoryHeight = this.lowerStoryHeight + this.storyHeight;
    }
    
 
    
    
}
