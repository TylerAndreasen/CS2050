package Program1Package;

public class Album
{
    public static final boolean ALBUM_CLASS_DEBUG_TOGGLE = true;
    private static int ALBUM_CLASS_COUNTER = 0;
    public static final String[] validGenres = {"easy listening", "hip-hop",
            "orchestral", "your parents", "theatre"};
    //The genre "easy listening" is used if an invalid genre value is passed in
    private static final
    String  defaultTitle = "Renaissance",
            defaultPerformer = "Beyonce",
            defaultGenre = "easy listening";
    private String
            title, //The album's title.
            performer, //The album's performer.
            genre; //The album's genre.

    private static final int defaultSongCount = 10;
    private int count, //The number of songs on the album.
                index; //the Program1Package.Album's index.
    Album()
    {
        this(defaultTitle, defaultPerformer, defaultGenre, defaultSongCount);
    }
    Album(String title, String performer, String genre, int count)
    {
        this.title = title;
        this.performer = (performer);
        this.setGenre(genre);
        this.setCount(count);
        this.index = ALBUM_CLASS_COUNTER;
        ALBUM_CLASS_COUNTER++;
    }
    public String toString()
    {
        return "\n\n===\nProgram1Package.Album["+this.index
                +"] is called :"+this.title
                +":.\nIt was preformed by :"+this.performer
                +":.\nAs a whole the album's genre is considered to be :"
                +this.genre+":.\nAnd contains :"+this.count+": (minimum 10)" +
                " songs."+(this.isLong() ? "(That's a lot!)" : " ");
    }

    //Getters
    public boolean isLong()
    {
        //From assignment "has more than 50 songs (false otherwise)."
        if (this.count > 50)
        {
            //if (ALBUM_CLASS_DEBUG_TOGGLE)
            // System.out.println("#DEBUG# Now That's What I Call A Long "
            // +"Program1Package.Album. #"); //Too Easy
            return true;
        } return false;
    }
    public String getTitle() {return this.title;}
    public String getPerformer() {return this.performer;}
    public String getGenre() {return this.genre;}
    public int getCount() {return this.count;}
    public int getIndex() {return this.index;}

    //Setters
    public boolean setTitle(String title)
    {
        title = title.trim();
        if (title.isEmpty())
        {
            if (ALBUM_CLASS_DEBUG_TOGGLE)
                System.out.println("#DEBUG# Attempted to push an empty value into the title of :"+this.title+":. #");
            return false;
        } else
        {
            this.title = title;
            return true;
        }
    }
    public boolean setPerformer(String performer)
    {
        performer = performer.trim();
        if (performer.isEmpty())
        {
            if (ALBUM_CLASS_DEBUG_TOGGLE)
                System.out.println("#DEBUG# Attempted to push an empty value into the performer name of :"+this.title+":. #");
            return false;
        } else
        {
            this.performer = performer;
            return true;
        }
    }
    public boolean setGenre(String genre)
    {
        genre = genre.trim();
        if (genre.isEmpty())
        {
            if (ALBUM_CLASS_DEBUG_TOGGLE)
                System.out.println("#DEBUG# Attempted to push an empty value into the genre of :"+this.title+":. #");
            return false;
        }
        int validIndex = Program1.contains(validGenres, genre);

        if (validIndex < 0)
        {
            this.genre = validGenres[0];
            if (ALBUM_CLASS_DEBUG_TOGGLE)
                System.out.println(
                        "#DEBUG# Input Genre :"+genre+": is invalid. See list of valid genres in Program1Package.Album.java." +
                                "Setting to default :"+validGenres[0]+":. #"
                );
            return false;
        }
        this.genre = validGenres[validIndex];
        return true;

    }
    public boolean setCount(int count)
    {
        if (count < defaultSongCount)
        {
            this.count = defaultSongCount;
            if (ALBUM_CLASS_DEBUG_TOGGLE)
                System.out.println("#DEBUG# Attempted to set album :"
                        +this.title+": to contain :"
                        +count+": total Songs. Minimum :"
                        +defaultSongCount+": #");
            return false;
        } else
        {
            this.count = count;
            return true;
        }
    }
}

