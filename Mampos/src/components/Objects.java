/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

/**
 *
 * @author Diego
 */
import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mampos.Utils;

public abstract class Objects {
	
	public static List<Muro> walls = new ArrayList<>();
	public static List<Story> stories = new ArrayList<>();
        public static List vertices = new ArrayList<>();
        public static Story currentStory;
        
        public static void rellenarVertices(){
            for(int i = 0; i < walls.size(); i++){
                float[] vert = walls.get(i).vertex;
                for(int j = 0; j < vert.length; j++){
                    vertices.add(vert[j]);
                }
                
            }
        }
	
	public static Muro getWalls(int index) {
		if(walls.size() > index) {
			return walls.get(index);
		}
		return null;
	}
	
	public static void addWall(Muro wall) {
		walls.add(wall);
	}
	
	public static Story[] getStories() {
            Story[] storiesArray = new Story[stories.size()];
            return stories.toArray(storiesArray);
	}
       
        
        public static void setStories(Object[][] data) {
            List<Story> storiesUpdated = new ArrayList<>();
            //Object[] storiesUpdatedArray = data.toArray();
            
            int counter = 1;
            for(int i = data.length-1; i >= 0; i--){
                data[i][0] = counter;
                if(counter !=  1){
                    float baseAntigua = Float.parseFloat(data[i+1][4].toString());
                    float altura = Float.parseFloat(data[i][5].toString());
                    float techoNuevo = baseAntigua + altura;
                    data[i][4] = techoNuevo; 
                    data[i][3] = baseAntigua;
                } 

                counter++;
            } 
            
            
            
            for(int i = 0; i < data.length; i++){
                if(i == 0){
                    components.Story story = new components.Story();
                    Object storyData = data[i];
                    List hola = mampos.Utils.convertObjectToList(storyData);
                    story.setParams(hola); 
                    storiesUpdated.add(story);
                } else {
                    components.Story story = new components.Story();
                    Object storyData = data[i];
                    List hola = mampos.Utils.convertObjectToList(storyData);
                    story.setParams(hola); 
                    storiesUpdated.add(story);
                }
                
                
            }
            stories = storiesUpdated;
	}
	
        
        public static void setStories(List<Story> data) {
            List<Story> storiesUpdated = new ArrayList<>();
            //Object[] storiesUpdatedArray = data.toArray();
            Object storiesData = data.toArray();
            List niveles = Utils.convertObjectToList(storiesData);
            
            
            
           for(int i = 0; i < niveles.size(); i++){
               
                Object jpña = niveles.get(i);
                LinkedTreeMap hola = (LinkedTreeMap) jpña;
                Collection sdfawe = hola.values();
                Object[] safdewfwf = sdfawe.toArray();
               
                components.Story story = new components.Story();
                Object storyData = safdewfwf;
                List hola2 = mampos.Utils.convertObjectToList(storyData);
                story.setParams(hola2); 
                storiesUpdated.add(story);
               
                
            }
            
            
      
            stories = storiesUpdated;
	}
	
        
	public static void addStory(Story story) {
            stories.add(story);
	}

}
