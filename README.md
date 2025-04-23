# cse237-project25

Team Members:
  - Nicole Jackson
  - Chuhan Qiao
  - Kyle Beedon

Iteration 3:
For each iteration you should answer the following:
  - What user stories were completed this iteration?
     -  A bank customer should be able to access an account management menu (Chuhan)
     -  A bank customer should be able to set a limit so they can't withdraw too much (Nicole)
     -  A bank customer should be able to go back on a given page with a back button (Kyle)
     -  A bank customer should not be able to rest their password with the same password (
  - What user stories do you intend to complete in the next iteration?
     - None
  - Is there anything that you implemented but doesn't currently work?
      - No
    - What commands are needed to compile and run your code from the command line (please provide a script that users can run to launch your program)?
      - Read .sh file


Iteration 2:
Team Members:
  - Nicole Jackson
  - Chuhan Qiao
  - Kyle Beedon
For each iteration you should answer the following:
  - What user stories were completed this iteration?
        Deposit:
            A bank customer can deposit money into their account.
            Validations are handled (e.g., non-negative input, frozen account restriction).
        overdraft fee:
            A bank customer can withdraw money.
            If funds are insufficient, an overdraft fee of $10 is applied.
        Frozen accounts cannot withdraw deposit.
            Check Balance:
            A bank customer can check their current balance at any time.
        Transfer Money:
            A customer can transfer money to another user’s account.
            Transfers are denied if the account is frozen or funds are insufficient.
            Transactions are logged for both sender and recipient.
        Transaction History:
            All actions (deposit, withdraw, transfer, freeze/unfreeze) are recorded and viewable by the user.
        Freeze Account:
            A customer can freeze or unfreeze their account.
            Frozen accounts cannot perform deposit, withdraw, or transfer actions.
        Delete Account:
            A customer can delete their account with confirmation.
            Account information is removed from the system.
    - What user stories do you intend to complete in the next iteration?
          Add free transfer limits (e.g., 3 free transfers/month, then charge a fee)
          Create admin account to view all users (future stretch)
          Persistent storage of users and balances (e.g., file or database)
    - Is there anything that you implemented but doesn't currently work?
      No
      Once launched:
      What commands are needed to compile and run your code from the command line (please provide a script that users can run to launch your program)?
            Main Menu:
            Choose 1 to login or create an account
            Choose 2 to exit
            Account Menu:
            1: Deposit
            2: Withdraw
            3: Check Balance
            4: Change Password
            5: Transfer Money
            6: Logout
            7: View Transaction History
            8: Freeze/Unfreeze Account
