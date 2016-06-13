package timetable.manager ;

import java.util.* ;

public interface URLDataStructureIF
{
    
    public String getURL(String course, String urlList) ;
    
    public void addURL(String urlList, String course, String URL) ;
    
    public ArrayList getCourses(String urlList) ;
    
    public ArrayList getURLDataLists() ;
    
    public void updateLists() ;
       
}
