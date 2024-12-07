package project;

import java.util.*;

public class VotingManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Stack voterStack = new Stack();
    private static List<String> voterEntries = new ArrayList<>();
    private static Candidate[] candidates = {
            new Candidate("Candidate A", "Party X", "President"),
            new Candidate("Candidate B", "Party Y", "Vice President"),
            new Candidate("Candidate C", "Party Z", "Secretary")
    };
    private static Admin admin = new Admin("admin123", "admin", "4176");
    private static boolean votingInProgress = false;
    private static Candidate winner = null;
    private static int maxVotes = 0;

    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    startVoting();
                    break;

                case 2:
                    stopVoting();
                    break;

                case 3:
                    checkVoteCounts();
                    break;

                case 4:
                    declareWinner();
                    break;

                case 5:
                    exitSystem();
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

        } while (choice != 5);
    }

    private static void displayMenu() {
        System.out.println("\nElection Menu:");
        System.out.println("1. Start Voting");
        System.out.println("2. Stop Voting");
        System.out.println("3. Check Vote Counts");
        System.out.println("4. Declare Winner");
        System.out.println("5. Exit");
    }

    private static void startVoting() {
        System.out.print("Enter admin username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter admin password: ");
        String enteredPassword = scanner.nextLine();

        if (admin.authenticate(enteredUsername, enteredPassword)) {
            startVotingProcess();
        } else {
            System.out.println("Incorrect admin credentials. Cannot start voting.");
        }
    }

    private static void startVotingProcess() {
        if (!votingInProgress) {
            resetCandidateVotes();
            System.out.println("Voting started. Voters can now cast their votes.");
            votingInProgress = true;
            performVoting();
        } else {
            System.out.println("Voting is already in progress.");
        }
    }

    private static void stopVoting() {
        System.out.print("Enter admin username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter admin password: ");
        String enteredPassword = scanner.nextLine();

        if (admin.authenticate(enteredUsername, enteredPassword)) {
            stopVotingProcess();
        }
    }

    private static void stopVotingProcess() {
        if (votingInProgress) {
            System.out.println("Voting stopped. No more votes can be cast.");
            votingInProgress = false;
        } else {
            System.out.println("Voting is not in progress.");
        }
    }

    private static void checkVoteCounts() {
        System.out.println("Vote Counts:");
        for (Candidate candidate : candidates) {
            System.out.println(candidate.name + " (" + candidate.partyName + "), " +
                    "Post: " + candidate.post + ": " + candidate.votes + " votes");
        }
    }

    private static void declareWinner() {
        if (votingInProgress) {
            System.out.println("Voting is still in progress. Cannot declare a winner yet.");
        } else {
            System.out.print("Enter admin username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter admin password: ");
            String enteredPassword = scanner.nextLine();

            if (admin.authenticate(enteredUsername, enteredPassword)) {
                declareWinnerProcess();
            } else {
                System.out.println("Incorrect admin credentials. Cannot declare a winner.");
            }
        }
    }

    private static void declareWinnerProcess() {
        if (winner != null) {
            System.out.println("Winner: " + winner.name + " (" + winner.partyName +
                    "), Post: " + winner.post);
        } else {
            System.out.println("No winner. No votes cast.");
        }
    }

    private static void exitSystem() {
        System.out.println("Exiting the Election Management System. Goodbye!");
        System.exit(0);
    }

    private static void resetCandidateVotes() {
        for (Candidate candidate : candidates) {
            candidate.votes = 0;
        }
    }

    private static void performVoting() {
        for (int i = 1; i <= getTotalVoters(); i++) {
            getVoterDetails(i);
            displayCandidates();
            int candidateNumber = getVoterChoice();
            scanner.nextLine(); // Consume the newline character after reading the candidate number
            voteForCandidate(candidateNumber);
        }
    }

    private static void getVoterDetails(int voterNumber) {
        System.out.println("Enter details for voter " + voterNumber + ":");
        String voterID = getVoterID();
        String cnic = getCNIC();
        String voterName = getVoterName();

        if (checkForDuplicateEntries(voterID, cnic)) {
            System.out.println("This voter has already voted. Multiple votes are not allowed.");
            getVoterDetails(voterNumber); // Re-enter details for the same voter
        } else {
            voterEntries.add(voterID + cnic);
        }
    }

    private static String getVoterID() {
        System.out.print("Voter ID: ");
        return scanner.nextLine();
    }

    private static String getCNIC() {
        System.out.print("CNIC: ");
        return scanner.nextLine();
    }

    private static String getVoterName() {
        System.out.print("Name: ");
        return scanner.nextLine();
    }

    private static boolean checkForDuplicateEntries(String voterID, String cnic) {
        return voterEntries.contains(voterID + cnic);
    }

    private static void displayCandidates() {
        System.out.println("Candidates:");
        for (int j = 0; j < candidates.length; j++) {
            System.out.println((j + 1) + ". " + candidates[j].name + " (" + candidates[j].partyName + ")");
            System.out.println("   Post: " + candidates[j].post);
        }
    }

    private static int getVoterChoice() {
        int candidateNumber;
        do {
            System.out.print("Enter the candidate number to vote: ");
            candidateNumber = scanner.nextInt();
            if (candidateNumber < 1 || candidateNumber > candidates.length) {
                System.out.println("Invalid candidate number. Please enter a valid number.");
            }
        } while (candidateNumber < 1 || candidateNumber > candidates.length);

        return candidateNumber;
    }


    private static void voteForCandidate(int candidateNumber) {
        if (candidateNumber >= 1 && candidateNumber <= candidates.length) {
            candidates[candidateNumber - 1].votes++;
            voterStack.push(new Voter(getVoterID(), getCNIC(), getVoterName()));
            System.out.println("Vote cast successfully!");

            if (candidates[candidateNumber - 1].votes > maxVotes) {
                maxVotes = candidates[candidateNumber - 1].votes;
                winner = candidates[candidateNumber - 1];
            }
        } else {
            System.out.println("Invalid candidate number. Vote not cast.");
        }

        System.out.println();
    }

    private static int getTotalVoters() {
        // You can change this based on the actual number of voters
        return 5;
    }
}
