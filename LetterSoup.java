import java.util.ArrayList;

class LetterSoup{

  private char[][] soup;
  private char[][] soup2;
  private ArrayList<String> words = new ArrayList<String>();

  // CONSTRUCTOR
  public LetterSoup(char[][] soup, char[][] soup2){
    this.soup  = soup;
    this.soup2 = soup2;
  }

  public String[] getWords(){
    return words.toArray(new String[words.size()]);
  }

  public boolean searchWord(String word){
    ArrayList<int[]> openPos =  new ArrayList<int[]>();
    ArrayList<int[]> closePos =  new ArrayList<int[]>();
    char firstLetter = word.charAt(0);

    for(int i=0; i<soup2.length; i++){
      for(int j=0; j<soup2[0].length; j++){
        if(soup2[i][j] == firstLetter){
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
    if(current[1]<soup2[0].length-1 && soup2[current[0]][current[1]+1] == letters[current[2]]){
      int[] next = {current[0],current[1]+1,current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }

    // DOWN
    if(current[0]<soup2.length-1 && soup2[current[0]+1][current[1]] == letters[current[2]]){
      int[] next = {current[0]+1,current[1],current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }
    // LEFT
    if(current[1]>0 && soup2[current[0]][current[1]-1] == letters[current[2]]){
      int[] next = {current[0],current[1]-1,current[2]+1};
      if(!subContains(closePos,next)) openPos.add(next);
    }
    // UP
    if(current[0]>0 && soup2[current[0]-1][current[1]] == letters[current[2]]){
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

  // NEW METHODS
  public boolean IsVowel(char c){
    if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') return true;
    return false;
  }

  public boolean containsCV(ArrayList<int[]> arrayPos, int[] values, int lenght){
    lenght += 2;
    boolean contains = true;
    for(int[] pos:arrayPos){
      for(int i=0; i<lenght; i++){
        if(i == 2) continue;
        contains = (contains && (pos[i] == values[i]));
      }
      if(contains) return contains;
    }
    return false;
  }

  public void searchCV(){
    ArrayList<int[]> openStates =  new ArrayList<int[]>();
    ArrayList<int[]> closedStates =  new ArrayList<int[]>();

    for(int i=0; i<soup.length; i++){
      for(int j=0; j<soup[0].length; j++){
        if(!IsVowel(soup[i][j])){ // THE WORD BEGIN WITH A CONSONAT

          // STATE = [row, col, current string lenght, ascii1, ascii2, ascii3, ascii4, 0 or 1 (consonant/vowel)]
          int ascii1 = (int) soup[i][j];
          int[] current = {i,j,1,ascii1,0,0,0,1}; // INITIAL STATE
          openStates.add(current);
          System.out.println("Estado inicial: "+i+","+j+","+ascii1+","+current[4]+","+current[5]+","+current[6]+","+current[7]);
          if(openStates.size() == 1){
            System.out.println("El largo de EA es 1");
          }

          // SEARCH A WORD IN BOTH SOUPS
          deepSearchCV(openStates.get(openStates.size()-1),openStates,closedStates);
          System.out.println("Se limpian los estados");
          openStates.clear();
          closedStates.clear();
        }
      }
    }
  }
  public boolean deepSearchCV(int[] current, ArrayList<int[]> openStates, ArrayList<int[]> closedStates){
    // DECODE CURRENT
    int i = current[0];
    int j = current[1];
    int lenght = current[2];

    System.out.println("La palabra que usa es: " + Character.toString((char)current[3])+Character.toString((char)current[4])+Character.toString((char)current[5])+Character.toString((char)current[6]));

    if(lenght == 4){
      String word = Character.toString((char)current[3])+Character.toString((char)current[4])+Character.toString((char)current[5])+Character.toString((char)current[6]);
      // SEARCH THE WORD IN THE SOUP2
      if(searchWord(word) && !words.contains(word)){
        words.add(word);
      }
    }

    // CLOSE THE CURRENT STATE
    closedStates.add(current);
    // REMOVE THE CURRENT POS FROM OPEN
    openStates.remove(openStates.size()-1);

    System.out.println("Se cierra el estados actual -> El largo de los abiertos es:" + openStates.size());

    // TRANSITIONS -------------------------------------------------------------
    //
    // VOWEL CASE
    if(current[7] == 1){
      // RIGHT
      if(j<soup[0].length-1 && IsVowel(soup[i][j+1])){
        int[] next = {i, j+1, lenght +1, current[3], current[4], current[5], current[6], 0};
        next[3+lenght] = (int) soup[i][j+1]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }

      // DOWN
      if(i<soup.length -1 && IsVowel(soup[i+1][j])){
        int[] next = {i+1, j, lenght +1, current[3], current[4], current[5], current[6], 0};
        next[3+lenght] = (int) soup[i+1][j]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }
      // LEFT
      if(j>0 && IsVowel(soup[i][j-1])){
        int[] next = {i, j-1, lenght +1, current[3], current[4], current[5], current[6], 0};
        next[3+lenght] = (int) soup[i][j-1]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }
      // UP
      if(i>0 && IsVowel(soup[i-1][j])){
        int[] next = {i-1, j, lenght +1, current[3], current[4], current[5], current[6], 0};
        next[3+lenght] = (int) soup[i-1][j]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates, next, lenght)) openStates.add(next);
      }
    }
    //
    // CONSONANT CASE
    else if (current[7] == 0){
      // RIGHT
      if(j<soup[0].length-1 && !IsVowel(soup[i][j+1])){
        int[] next = {i, j+1, lenght +1, current[3], current[4], current[5], current[6], 1};
        next[3+lenght] = (int) soup[i][j+1]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }

      // DOWN
      if(i<soup.length -1 && !IsVowel(soup[i+1][j])){
        int[] next = {i+1, j, lenght +1, current[3], current[4], current[5], current[6], 1};
        next[3+lenght] = (int) soup[i+1][j]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }
      // LEFT
      if(j>0 && !IsVowel(soup[i][j-1])){
        int[] next = {i, j-1, lenght +1, current[3], current[4], current[5], current[6], 1};
        next[3+lenght] = (int) soup[i][j-1]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }
      // UP
      if(i>0 && !IsVowel(soup[i-1][j])){
        int[] next = {i-1, j, lenght +1, current[3], current[4], current[5], current[6], 1};
        next[3+lenght] = (int) soup[i-1][j]; // SAVE THE ASCII VALUE
        if(!containsCV(closedStates,next, lenght)) openStates.add(next);
      }
    }
    //
    // END TRANSITIONS ---------------------------------------------------------

    // NO MORE OPEN POSIBILITIES
    if(openStates.size() == 0) return true; // TRUE MEANS SEARCH COMPLETE!

    return deepSearchCV(openStates.get(openStates.size()-1), openStates, closedStates);
  }
}
