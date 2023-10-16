import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/*
 Author: Huria
 Date: sep / 30


 */
public class ElectionResults {


    // the main method works as follows:
    // - provided code (leave this code as is):
    //   - prompts user for file name containing ballot data
    //   - reads data into array (one array item per line in file)
    //   - runs any testing code that you have written
    // - code you need to write:
    //   - execute the Ranked Choice Voting process as outlined
    //     in the project description document by calling the other
    //     methods that you will implement in this project
    public static void main(String[] args) {
        // Establish console Scanner for console input
        Scanner console = new Scanner(System.in);

        // Determine the file name containing the ballot data
        System.out.print("Ballots file: ");
        String fileName = console.nextLine();

        // Read the file contents into an array.  Each array
        // entry corresponds to a line in the file.
        String[] fileContents = getFileContents(fileName);
        convert(fileContents);
        tallies(convert(fileContents));
        ArrayList<Ballot> ballotList = convert(fileContents);
        HashMap<String, Integer> talliesList = tallies(ballotList);

        countTotalVotes(talliesList);
        analyze((talliesList));
        //printCounts((talliesList));


        boolean continueProcessing = true;
        while (continueProcessing) {
            //HashMap<String, Integer> tally = tallies(ballotList);
            talliesList = tallies(ballotList);
//            System.out.println(talliesList);
            Result result = analyze(talliesList);
//            System.out.println(result);

            printCounts((talliesList));

            if (result.isWinner()) {
                System.out.println("Winner: " + result.getName());
                printPercentages(talliesList);

                break;
            } else {
                System.out.println("Remove1 " + result.getName());
                remove(result.getName(), ballotList);

            }
        }

        //printPercentages(tallies(convert(fileContents)));


        // ***********************************************
        // Your code below here: execute the RCV process,
        // ensuring to make use of the remaining methods
        // ***********************************************
    }


    // Create your methods below here
    public static ArrayList<Ballot> convert(String[] filesContents) {
        ArrayList<Ballot> ballotList = new ArrayList<>();

        for (String line : filesContents) {
            String[] candidateNames = line.split(",");
            ArrayList<String> candidateList = new ArrayList<>(Arrays.asList(candidateNames));
            Ballot ballot = new Ballot();
            for (String candidate : candidateList) {
                ballot.addCandidate(candidate);
            }
            ballotList.add(ballot);
        }

        return ballotList;
    }

    public static HashMap<String, Integer> tallies(ArrayList<Ballot> ballots) {
        HashMap<String, Integer> voteCounts = new HashMap<>();

        for (Ballot ballot : ballots) {
            String firstPreference = ballot.getCurrentChoice();
            voteCounts.put(firstPreference, voteCounts.getOrDefault(firstPreference, 0) + 1);
        }
        return voteCounts;
    }

    public static int countTotalVotes(HashMap<String, Integer> voteCounts) {
        int totalVotes = 0;

        // Iterate through the vote counts and sum the total votes
        for (int votes : voteCounts.values()) {
            totalVotes += votes;
        }
        return totalVotes;
    }


    public static Result analyze(HashMap<String, Integer> voteCounts) {
        int totalVotes = countTotalVotes(voteCounts);

        // Determine how many votes are needed to win (assuming simple majority)
        int votesToWin = (totalVotes / 2) + 1;
        int leastNumVotes = 1000000;
//        boolean FoundWinner = false;
        Result result = null;

        // Check if any candidate has enough votes to win
        ArrayList<String> candidates = new ArrayList<String>(voteCounts.keySet());
        for (String candidate : voteCounts.keySet()) {
            int candidateVotes = voteCounts.get(candidate);
            if (candidateVotes >= votesToWin) {
                result = new Result(candidate, true);
                return result;
                //System.out.print(candidate);
                //System.out.println("winner");

            } else {
                if (candidateVotes < leastNumVotes) {
                    leastNumVotes = candidateVotes;
                    result = new Result(candidate, false);
//                    System.out.print(candidate);
//                    System.out.println(" --loser");

                }
            }
        }
//        if (result.isLoser()) {
//            candidates = remove(result.getName(), candidates);
//        }
//        System.out.println(result);

        return result;
    }


    public static void printCounts(HashMap<String, Integer> voteCounts) {
        System.out.println("Vote Tallies" );
        for (String candidatee : voteCounts.keySet()) {
            int votes = voteCounts.get(candidatee);
            System.out.println(candidatee + ": " + votes);
        }
    }

//    Method name: remove()
//Goal: This method is called if no one wins a round of voting and the losing candidate needs to be removed from all votes cast.
//Inputs:
//A String with the name of the candidate to be removed
//An ArrayList of Ballot objects representing all ballots cast
//Output: Nothing
// Tasks:
//For each Ballot in the given ArrayList, remove the given candidate name from the Ballot
//If the Ballot is empty (no more candidates that were on the Ballot exist any more), then the Ballot
// should be removed from the ArrayList entirely.
//HINT: ArrayList removals can be tricky! Be sure you review how to do this.
    public static void remove(String CandidateName, ArrayList<Ballot> ballots) {
        Iterator<Ballot> iterator = ballots.iterator();
        while (iterator.hasNext()) {
            Ballot ballot = iterator.next();
            ballot.removeCandidate(CandidateName);
            if (ballot.isExhausted()) {
                iterator.remove();
                //Ballot x = iterator.next();
            }
        }
    }


//        for (int i=0; i < 3; ++i) {
//            if (iterator.equals(candidateName)) {
//               Ballot.removeCandidate(candidateName);
////           }
//        }
    // this code might work
//        for (Ballot ballot : ballots) {
//            ballot.removeCandidate(CandidateName);
//            if (ballot.isExhausted()) {
//                ballots.remove(ballot);
    // it ends here
//        Iterator<Ballot> iterator;
//        iterator = ballots.iterator();
//
//        while (iterator.hasNext()) {
//            Ballot ballot = iterator.next();
//
//            // Remove the candidate from the current Ballot
//            ballot.removeCandidate(CandidateName);
//            if (ballot.isExhausted()) {
//                iterator.remove();
//            }


            // If the Ballot is empty, remove it from the ArrayList
//            if (ballot.isEmpty()) {
//                iterator.remove();
//            }
//        for (String candidate : voteCounts.keySet()) {
//           if (candidate.equals(candidateName)) {
//               ballot.removeCandidate(candidateName);
//           }
//        Iterator<Ballot> iterator;
//
//        while (iterator.hasNext()) {
//            Ballot ballot = iterator.next();
//
//            ballot.removeCandidate(candidateName);
//
//
//            //System.out.println("candidateName:"+ candidateName);
//            if (ballot.isExhausted()) {
//                iterator.remove();
//            }

//            candidates.remove(candidateName);
//            }
//
//        }
//        Iterator<Ballot> iterator;
//        iterator = ballots.iterator();
//        while (iterator.hasNext()) {
//            Ballot ballot = iterator.next();
//            System.out.println(candidateName + "asdf");
//            ballot.removeCandidate(candidateName);
//            if (ballot.isExhausted()) {
//                iterator.remove();



    public static void printPercentages(HashMap<String, Integer> voteCounts) {
        int totalVotes = countTotalVotes(voteCounts);

        System.out.println("Vote Percentages");
        for (String candidate : voteCounts.keySet()) {
            int votes = voteCounts.get(candidate);
            double percentage = (votes * 100.0) / totalVotes;
            System.out.printf("%.1f%% %s\n", percentage, candidate);
            }
        }
    

    // DO NOT edit the methods below. These are provided to help you get started.
    public static String[] getFileContents (String fileName) {

        // first pass: determine number of lines in the file
        Scanner file = getFileScanner(fileName);
        int numLines = 0;
        while (file.hasNextLine()) {
            file.nextLine();
            numLines++;
        }

        // create array to hold the number of lines counted
        String[] contents = new String[numLines];

        // second pass: read each line into array
        file = getFileScanner(fileName);
        for (int i = 0; i < numLines; i++) {
            contents[i] = file.nextLine();
        }

        return contents;
    }


    public static Scanner getFileScanner(String fileName) {
        try {
            FileInputStream textFileStream = new FileInputStream(fileName);
            Scanner inputFile = new Scanner(textFileStream);
            return inputFile;
        }
        catch (IOException ex) {
            System.out.println("Warning: could not open " + fileName);
            return null;
        }
    }
}
