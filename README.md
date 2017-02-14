DISTRIBUTED BANKING SYSTEM USER MANUAL

Steps to use:

1. Navigate to project dir: "cd Documents/workspace/DistributedBankingSystem/src/"

2. Compile all source files on cmd: "javac business/Transaction.java business/Account.java client/ATM.java interfaces/IBank.java interfaces/IStatement.java server/Bank.java server/Statement.java "

3. Run RMIRegistry on port of choice: "rmiregistry 7777 &"

4. Run Bank Server : java server/Bank 7777

5. Open New (2nd) Command Window & Navigate to source dir : "cd Documents/workspace/DistributedBankingSystem/src/

6. Run Client Program as you wish: java client/ATM localhost 7777

