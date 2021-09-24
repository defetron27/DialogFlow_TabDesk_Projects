import Utils.Constant;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class DialogWebHookHandler implements RequestStreamHandler
{
    private JSONParser jsonParser = new JSONParser();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String jsonResponseString;

        String fallbackMessage = "Oops.. there was some server or internal problem, don't worry please say that name again.";

        try
        {
            JSONObject jsonRequestObject = (JSONObject) jsonParser.parse(bufferedReader);

            if (jsonRequestObject.get(Constant.queryResult) != null)
            {
                JSONObject jsonQueryResult = (JSONObject) jsonRequestObject.get(Constant.queryResult);

                if (jsonQueryResult.get(Constant.intent) != null)
                {
                    JSONObject jsonIntent = (JSONObject) jsonQueryResult.get(Constant.intent);

                    if (jsonIntent.get(Constant.displayName) != null)
                    {
                        String jsonDisplayName = (String) jsonIntent.get(Constant.displayName);

                        switch (jsonDisplayName)
                        {
                            case "GetWord" :

                                if (jsonQueryResult.get(Constant.parameters) != null)
                                {
                                    JSONObject jsonParameters = (JSONObject) jsonQueryResult.get(Constant.parameters);

                                    if (jsonParameters.get(Constant.word) != null)
                                    {
                                        String word = (String) jsonParameters.get(Constant.word);

                                        if (word != null && !word.equals("") && !word.equals("null"))
                                        {
                                            String getWord = getResponseForWord(word);
                                        }
                                    }
                                }

                                break;
                            case "Default Welcome Intent" :

                                String welcomeResponse = "Hi, welcome to Tap Alpha. " +
                                        "It's a pleasure to talk to you." +
                                        "Ok, what is tab alpha?. " +
                                        "Tab Alpha is a multi level alphabetic word game." +
                                        "Ok, i will give short examples to how to select and play the levels." +
                                        "Here's seven types of levels are there." +
                                        "First, \"starting word\"." +
                                        "Second, \"ending word\"." +
                                        "Third, \"starting and ending word\"." +
                                        "Fourth, \"starting word with size\"." +
                                        "Fifth, \"ending word with size\"." +
                                        "Sixth, \"starting and ending word with size\". And last " +
                                        "Seventh one is \"word with size\"." +
                                        "If you want more information or help to play the levels, say \"'the level name' with help\" or \"how to play 'the level name'\"." +
                                        "Ok, how to start the game, so simple, choose any of the level name from above one's and say to me. I will start that word game level." +
                                        "Ok, i trust above instructions are useful to you. If you have more help or instructions, say help" +
                                        "Ok, if you ready to play, say any level name to play.";



                                break;

                            case "StartingWordIntent" :

                                String startingWordResponse = "Ok, you choose starting word, i trust you know all the instructions to how to play the starting word level, if you don't know, say \"how to play starting word\". If you know all the instructions say name to start the game.";



                                break;
                            case "EndingWordIntent" :

                                String endingWordResponse = "Ok, you choose ending word, i trust you know all the instructions to how to play the ending word level, if you don't know, say \"how to play ending word\". If you know all the instructions say name to start the game.";



                                break;
                            case "StartingAndEndingWordIntent" :

                                String startingAndEndingWordResponse = "Ok, you choose starting word, i trust you know all the instructions to how to play the starting and ending word level, if you don't know, say \"how to play starting and ending word\". If you know all the instructions say name to start the game.";



                                break;
                            case "StartingWordIntentWithSize" :

                                String startingWordWithSizeResponse = "Ok, you choose starting word, i trust you know all the instructions to how to play the starting word with size level, if you don't know, say \"how to play starting word with size\". If you know all the instructions say name to start the game.";



                                break;
                            case "EndingWordIntentWithSize" :

                                String endingWordWithSizeResponse = "Ok, you choose starting word, i trust you know all the instructions to how to play the starting word level, if you don't know, say \"how to play starting word\". If you know all the instructions say name to start the game.";



                                break;
                            case "StartingAndEndingWordWithSizeIntent" :

                                String startingAndEndingWordWithSizeResponse = "Ok, you choose starting word, i trust you know all the instructions to how to play the starting word level, if you don't know, say \"how to play starting word\". If you know all the instructions say name to start the game.";


                                break;
                            case "WordWithSizeIntent" :

                                String wordWithSizeResponse = "Ok, you choose starting word, i trust you know all the instructions to how to play the starting word level, if you don't know, say \"how to play starting word\". If you know all the instructions say name to start the game.";


                                break;
                        }
                    }
                }
            }
        }
        catch (ParseException e)
        {

        }
    }

    private String getResponseForWord(String word)
    {
        try
        {
            String letter = word.substring(word.length() - 1);

            URL urlDetail = new URL("https://api.datamuse.com/words?sp=" + letter + "*");

            HttpsURLConnection connection = (HttpsURLConnection) urlDetail.openConnection();

            connection.setDoOutput(true);

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "application/json");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            StringBuilder resultBuilder = new StringBuilder();

            String jsonOutput;

            while ((jsonOutput = bufferedReader.readLine()) != null)
            {
                resultBuilder.append(jsonOutput);
            }

            return resultBuilder.toString();
        }
        catch (IOException exception)
        {
            String[] a = {"Adorable","Adventurous","Aggressive","Agreeable","Alert","Alive","Amused","Angry","Annoyed","Annoying","Anxious","Arrogant","Ashamed","Attractive","Average","Awful"};

            String[] b = {"Bad","Beautiful","Better","Bewildered","Black","Bloody","Blue","Blue-eyed","Blushing","Bored","Brainy","Brave","Breakable","Bright","BusyBad","Beautiful","Better","Bewildered","Black","Bloody","Blue","Blue-eyed","Blushing","Bored","Brainy","Brave","Breakable","Bright","Busy"};

            String[] c = {"Calm", "Careful", "Cautious", "Charming", "Cheerful", "Clean", "Clear", "Clever", "Cloudy", "Clumsy", "Colorful", "Combative", "Comfortable", "Concerned", "Condemned", "Confused", "Cruel", "Curious", "Cute"};

            String[] d = {"Dangerous","Dark","Dead","Defeated","Defiant","Delightful","Depressed","Determined","Different","Difficult","Disgusted","Distinct","Disturbed","Dizzy","Doubtful","Drab","Dull"};

            String[] e = {"Eager","Easy","Elated","Elegant","Embarrassed","Enchanting","Encouraging","Energetic","Enthusiastic","Envious","Evil","Excited","Expensive","Exuberant"};

            String[] f = {"Fair","Faithful","Famous","Fancy","Fantastic","Fierce","Filthy","Fine","Foolish","Fragile","Frail","Frantic","Friendly","Frightened","Funny"};

            String[] g = {"Gentle","Gifted","Glamorous","Gleaming","Glorious","Good","Gorgeous","Graceful","Grieving","Grotesque","Grumpy"};

            String[] h = {"Handsome","Happy","Healthy","Helpful","Helpless","Hilarious","Homeless","Homely","Horrible","Hungry","Hurt"};

            String[] i = {"Ill","Important","Impossible","Inexpensive","Innocent","Inquisitive","Itchy"};

            String[] j = {"Jealous","Jittery","Jolly","Joyous"};

            String[] k = {"kind","king"};

            String[] l = {"Lazy","Light","Lively","Lonely","Long","Lovely","Lucky"};

            String[] m = {"Magnificent","Misty","Modern","Motionless","Muddy","Mushy","Mysterious"};

            String[] n = {"Nasty","Naughty","Nervous","Nice","Nutty"};

            String[] o = {"Obedient","Obnoxious","Odd","Old-fashioned","Open","Outrageous","Outstanding"};

            String[] p = {"Panicky","Perfect","Plain","Pleasant","Poised","Poor","Powerful","Precious","Prickly","Proud","Puzzled"};

            String[] q = {"Quant"};

            String[] r = {"Real","Relieved","Repulsive","Rich"};

            String[] s = {"Scary","Selfish","Shiny","Shy","Silly","Sleepy","Smiling","Smoggy","Sore","Sparkling","Splendid","Spotless","Stormy","Strange","Stupid","Successful","Super"};

            String[] t = {"Talented","Tame","Tender","Tense","Terrible","Tasty","Thankful","Thoughtful","Thoughtless","Tired","Tough","Troubled"};

            String[] u = {"Ugliest","Ugly","Uninterested","Unsightly","Unusual","Upset","Uptight"};

            String[] v = {"Victorious","Vivacious"};

            String[] w = {"Wandering","Weary","Wicked","Wide-eyed","Wild","Witty","Worrisome","Worried","Wrong"};

            String[] x = {"xeroxed","xeroxes","xiphoid","xanthic","xylidin","xerarch","xenopus","xylitol","xylenes"};

            String[] y = {"youthfulnesses","yellowhammers","yellowthroats","yellowhammer","yellowthroat","yeastinesses","yesternights","yoctoseconds","youthfulness","youngberries"};

            String[] z = {"Zany","Zealous"};



            return "";
        }
    }
}
