# NexaBank — Banking System Simulation
## Java OOP + Swing GUI Project

---

## 📁 Project Structure

```
BankingSystem/
├── BankAccount.java       ← Abstract base class (Inheritance root)
├── SavingsAccount.java    ← Derived: enforces ₹500 minimum balance
├── CurrentAccount.java    ← Derived: allows ₹10,000 overdraft
├── BankService.java       ← Business logic layer
├── FileHandler.java       ← Serialization: save/load from accounts.dat
└── BankingApp.java        ← Main Swing GUI (run this!)
```

---

## 🏛️ OOP Concepts Applied

| Concept         | Where                                              |
|-----------------|----------------------------------------------------|
| Inheritance     | `SavingsAccount`, `CurrentAccount` extend `BankAccount` |
| Polymorphism    | `withdraw()` overridden differently in each subclass |
| Encapsulation   | `protected` fields, public getters                 |
| Abstraction     | `BankAccount` is abstract; forces `withdraw()` impl |

---

## ⚙️ How to Compile & Run

> Requires: JDK 11+ (not just JRE — needs `javac`)

```bash
# Step 1 — Compile all files
cd BankingSystem
javac *.java

# Step 2 — Run the GUI
java BankingApp
```

---

## 🖥️ GUI Features

| Button           | What it does                                      |
|------------------|---------------------------------------------------|
| ⊕ Create Account | Creates Savings or Current account, saves to file |
| ↑ Deposit        | Adds money; persists immediately                  |
| ↓ Withdraw       | Validates rules per account type, then saves      |
| ≡ Display Account| Shows single account details                      |
| ❖ List All       | Shows every account in the system                 |

---

## 📋 Business Rules

- **Savings Account** — Minimum balance of ₹500 must be maintained
- **Current Account** — Overdraft up to ₹10,000 allowed (balance can go to -₹10,000)
- **Persistence** — All accounts saved to `accounts.dat` (Java Serialization)
  - Data survives across application restarts automatically

---

## 🧪 Quick Test Walkthrough

1. **Create** — AccNo: `ACC001`, Name: `Riya Sharma`, Type: SAVINGS, Amount: `5000`
2. **Deposit** — AccNo: `ACC001`, Amount: `2000` → Balance = ₹7,000
3. **Withdraw** — AccNo: `ACC001`, Amount: `6000` → Balance = ₹1,000 ✔
4. **Withdraw** — AccNo: `ACC001`, Amount: `1000` → ✘ Below ₹500 minimum
5. **Create** — AccNo: `ACC002`, Type: CURRENT, Amount: `1000`
6. **Withdraw** — AccNo: `ACC002`, Amount: `8000` → Balance = -₹7,000 ✔ (overdraft)

---

## 📦 File Persistence

Accounts are saved to `accounts.dat` in the working directory using Java's
built-in `ObjectOutputStream` / `ObjectInputStream`. All classes implement
`Serializable`. The file is updated after every Create / Deposit / Withdraw.
