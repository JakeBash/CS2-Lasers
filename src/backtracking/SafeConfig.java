package backtracking;

import model.LasersModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Stack;

/**
 * The class represents a single configuration of a safe.  It is
 * used by the backtracker to generate successors, check for
 * validity, and eventually find the goal.
 *
 * This class is given to you here, but it will undoubtedly need to
 * communicate with the model.  You are free to move it into the model
 * package and/or incorporate it into another class.
 *
 * @author Sean Strout @ RIT CS
 * @author YOUR NAME HERE
 */
public class SafeConfig implements Configuration {
    Collection<Configuration> configs = new ArrayList<Configuration>();// lists of configurations
    LasersModel model ;//laser model
    private int currentrow;
    private int currentcol;
    private boolean isdone=false;

    public SafeConfig(String filename) {
        this.model = new LasersModel(filename);// creates the ;asermodel
        this.currentrow=0;//ells us what the current row is
        this.currentcol=0;// tells us what the current column is.
        configs.add(this);
    }

    public SafeConfig(SafeConfig other){
        this.model = other.model;
        this.currentrow=other.currentrow;
        this.currentcol=other.currentcol;
    }

    private String[] findspace(int r, int c){
        String[]locations = new String[4];
        int counter = 0;
        if(r > 0)
        {
            if(this.model.getBoard()[r-1][c].equals("."))
            {
                locations[counter]=""+(r-1)+"   "+c+"";
                counter+=1;
            }
        }
        if(r < this.model.getRSize()-1)
        {
            if(this.model.getBoard()[r+1][c].equals("."))
            {
                locations[counter]=""+(r+1)+"   "+c+"";
                counter+=1;
            }
        }
        if(c > 0)
        {
            if(this.model.getBoard()[r][c-1].equals("."))
            {
                locations[counter]=""+r+"   "+(c-1)+"";
                counter+=1;
            }
        }
        if(c <this.model.getCSize()-1 )
        {
            if(this.model.getBoard()[r][c+1].equals("."))
            {
                locations[counter]=""+r+"  "+(c+1)+"";
            }
        }
        String[] spots = new String[counter];
        int i=0;
        while (i<counter){
            spots[i]=locations[i];
            i+=1;
        }
        return spots;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        // TODO
        String[][] map=this.model.getBoard();
        if(isGoal()){
            isdone=true;
            return configs;
        }
        for (int row=this.currentrow;row<this.model.getRSize();row++){
            for (int col=0;col<this.model.getCSize();col++){
                if (map[row][col].equals("X")){
                    String[] spots=findspace(row,col);
                    if(spots.length>0){
                        for (int i=0;i<spots.length;i++){
                            Scanner sc = new Scanner(spots[i]);
                            int r = sc.nextInt();
                            int c = sc.nextInt();
                            this.model.add(r,c);
                            if(this.model.laserVer(r,c)){
                                SafeConfig safeConfig = new SafeConfig(this);
                                configs.add(safeConfig);
                                safeConfig.currentrow=row;
                                configs.addAll(safeConfig.getSuccessors());
                                if(isdone){
                                    return configs;
                                }
                                else{
                                    configs.remove(safeConfig);
                                    this.model.remove(r,c);
                                }
                            }
                            else {
                                this.model.remove(r,c);
                            }
                        }
                    }
                }
                else if(map[row][col].matches("[0-9]")&&!this.model.pillarVer(row,col)){
                    String[] spots=findspace(row,col);
                    if(spots.length>0){
                        for (int i=0;i<spots.length;i++){
                            Scanner sc = new Scanner(spots[i]);
                            int r = sc.nextInt();
                            int c = sc.nextInt();
                            this.model.add(r,c);
                            if(this.model.laserVer(r,c)){
                                SafeConfig safeConfig = new SafeConfig(this);
                                configs.add(safeConfig);
                                safeConfig.currentrow=row;
                                configs.addAll(safeConfig.getSuccessors());
                                if(isdone){
                                    return configs;
                                }
                                else{
                                    configs.remove(safeConfig);
                                    this.model.remove(r,c);
                                }
                            }
                            else {
                                this.model.remove(r,c);
                            }
                        }
                    }

                }
            }
        }
        return configs;
    }

    @Override
    public boolean isValid() {
        // TODO
        String point;
        for (int row = 0; row <this.model.getRSize(); row++)
        {
            for (int col = 0; col < this.model.getCSize(); col++)
            {
                point = this.model.getBoard()[row][col];
                if(point.equals("L"))
                {
                    if(!this.model.laserVer(row,col))
                    {
                        return false;
                    }
                }
                if(point.matches("[0-9]"))
                {
                    if(!this.model.pillarVer(row, col))
                    {
                        return false;
                    }
                }
                if (point.equals(".")){
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isGoal() {
        // TODO
        if(isValid()){
            for (int row = 0; row <this.model.getRSize(); row++)
            {
                for (int col = 0; col < this.model.getCSize(); col++)
                {
                    String l = this.model.getBoard()[row][col];
                    if(l.equals("."))
                    {
                        return false;
                    }
                }
            }
        }
        else{
            return false;
        }
        return true;
    }
}
