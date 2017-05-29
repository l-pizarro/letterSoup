import java.util.ArrayList;

class LetterSoup{

  private char[][] soup;
  private String[] words;
  private ArrayList<String> results = new ArrayList<String>();

  // CONSTRUCTOR
  public LetterSoup(char[][] soup,String[] words){
    this.soup  = soup;
    this.words = words;
  }

  public String[] getResults(){
    return results.toArray(new String[results.size()]);
  }

  public void calculateResults(){
    // ALSO SORT THE SOLTION'S TEXT
    results.add("\nPalabras que no se encuentran en la sopa:");
    for(String word:words){
      if(searchWord(word)) results.add(0,word);
      else results.add(word);
    }

    results.add(0,"Palabras que se encuentran en la sopa:");
  }

  public boolean searchWord(String word){
    ArrayList<int[]> openPos =  new ArrayList<int[]>();
    ArrayList<int[]> closePos =  new ArrayList<int[]>();
    char firstLetter = word.charAt(0);

    for(int i=0; i<soup.length; i++){
      for(int j=0; j<soup[0].length; j++){
        if(soup[i][j] == firstLetter){
          int[] current = {i,j,1};
          openPos.add(current); // INITIAL STATE
          if(deepSearch(openPos.get(openPos.size()-1),word.toCharArray(),openPos,closePos)) return true;
          openPos.clear();
          closePos.clear();
        }
      }
    }

    return false;
  }

  public boolean deepSearch(int[] current, char[] letters, ArrayList<int[]> openPos, ArrayList<int[]> closePos){
    // FINAL STATE
    if(current[2] == letters.length) return true;

    // CLOSE THE CURRENT STATE
    closePos.add(current);
    // REMOVE THE CURRENT POS FROM OPEN
    openPos.remove(openPos.size()-1);

    // TRANSITIONS -------------------------------------------------------------
    //
    // RIGHT
    if(current[1]<soup[0].length-1 && soup[current[0]][current[1]+1] == letters[current[2]]){
      int[] next = {current[0],current[1]+1,current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }

    // DOWN
    if(current[0]<soup.length-1 && soup[current[0]+1][current[1]] == letters[current[2]]){
      int[] next = {current[0]+1,current[1],current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }
    // LEFT
    if(current[1]>0 && soup[current[0]][current[1]-1] == letters[current[2]]){
      int[] next = {current[0],current[1]-1,current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }
    // UP
    if(current[0]>0 && soup[current[0]-1][current[1]] == letters[current[2]]){
      int[] next = {current[0]-1,current[1],current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }
    //
    // END TRANSITIONS ---------------------------------------------------------

    // NO MORE OPEN POSIBILITIES
    if(openPos.size() == 0) return false;

    return deepSearch(openPos.get(openPos.size()-1),letters , openPos, closePos);
  }

  public boolean subContains(ArrayList<int[]> arrayPos, int[] values){
    for(int[] pos:arrayPos){
      if(pos[0] == values[0] && pos[1] == values[1]) return true;
    }

    return false;
  }

}
