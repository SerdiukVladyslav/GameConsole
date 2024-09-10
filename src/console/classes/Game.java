package console.classes;

public class Game
{
    public enum Genre
    {
        ACTION, SPORT, RACE
    }

    public enum Type
    {
        VIRTUAL, PHYSICAL
    }

    private final String name;
    private final Genre genre;
    private final Type type;

    private Game(String name, Genre genre, Type type)
    {
        this.name = name;
        this.genre = genre;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public Type getType()
    {
        return type;
    }

    public static class GameDisk
    {
        private final String description;
        private final Game data;

        private GameDisk(String name, Genre genre, String description)
        {
            this.description = description;
            this.data = new Game(name, genre, Type.PHYSICAL);
        }

        public String getDescription()
        {
            return description;
        }

        public Game getData()
        {
            return data;
        }
    }

    public static class VirtualGame
    {
        private final Game data;
        private int rating;

        private VirtualGame(String name, Genre genre)
        {
            this.data = new Game(name, genre, Type.VIRTUAL);
        }

        public Game getData()
        {
            return data;
        }

        public int getRating()
        {
            return rating;
        }

        public void setRating(int rating)
        {
            this.rating = rating;
        }
    }

    public static GameDisk getDisk(String name, Genre genre, String description)
    {
        return new GameDisk(name, genre, description);
    }

    public static VirtualGame getVirtualGame(String name, Genre genre)
    {
        return new VirtualGame(name, genre);
    }
}
