# GitHub Repo Creator
 
## Summary
 
A Java Swing GUI application that converts a local project folder into a Git repository and publishes it to GitHub. The user selects a folder, enters a repo name, description, and visibility, and the tool initializes Git, adds a `.gitignore` and `README.md`, creates an initial commit, creates a matching GitHub repo, and pushes to it.
 
## How to Run (Developer)
 
**Requirements:**
- Java JDK 11 or higher
- Git installed and available on your system PATH
- GitSubprocessClient (latest version) — download from [repo link]
- GitHubApiClient (latest version) — download from [repo link]
**Steps:**
1. Clone this repository
2. Download the GitSubprocessClient and GitHubApiClient `.jar` files from their respective GitHub repos under Releases
3. Add both `.jar` files to your project as external libraries (do not commit them)
4. Run `Main.java`
 