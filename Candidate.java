package project;

public class Candidate {
	String name;
    String partyName;
    String post;
    int votes;

    public Candidate(String name, String partyName, String post) {
        this.name = name;
        this.partyName = partyName;
        this.post = post;
        this.votes = 0;
    }
}
