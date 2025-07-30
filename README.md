# 🕸️ Spiderverse: A Graph-Theoretic Simulation of the Multiverse

This Java project models the **Spiderverse universe as a directed graph**, allowing for simulations of traversal, anomaly detection, clustering, and multiversal event analysis. It uses **core data structures and algorithms** from computer science (not external libraries), making it a strong demonstration of algorithmic thinking and object-oriented design.

## 🔍 Purpose

To simulate multiverse traversal, detect anomalies, analyze dimensional structures, and model key events in the Spiderverse using Java.

---

## 🧠 Key Concepts & Skills Demonstrated

| Skill / Concept             | Application in Project                                                |
|----------------------------|------------------------------------------------------------------------|
| **Breadth-First Search (BFS)** | Used in `BFS.java` and `TrackSpot.java` to find shortest paths between dimensions and track Spot’s location. |
| **Depth-First Search (DFS)** | Implemented in `DFS.java` to explore all paths for anomaly detection and canonical event analysis. |
| **Graph Data Structures**   | Custom-built using adjacency lists to represent dimensions and connections between them. |
| **File I/O Handling**       | Java input and output streams are used for reading `.in` files and writing `.out` reports. |
| **Clustering Algorithm**    | `Clusters.java` groups related dimensions based on connectivity logic. |
| **Object-Oriented Programming** | All logic modularized into reusable classes like `Person`, `Collider`, `GoHomeMachine`, `CanonEvent`, etc. |
| **Simulation Modeling**     | Custom logic simulates multiversal events like anomaly spread and character tracking. |

---

## 📂 File Structure

```
src/spiderman/              # Core Java source code (BFS, DFS, etc.)
bin/spiderman/              # Compiled bytecode (.class)
*.in                        # Input files (dimensions, anomalies, hubs, etc.)
*.out                       # Output results (clusters, reports, tracking)
.vscode/                    # Debug configuration for local runs
```

---

## 🚀 How to Run

1. **Compile Java Source Files**:
   ```bash
   javac -d bin src/spiderman/*.java
   ```

2. **Run Specific Modules**:
   ```bash
   java -cp bin spiderman.TrackSpot < spot.in > trackspot.out
   ```

   Replace `TrackSpot` and `spot.in` with any other module and input file to run different simulations.

---

## 🧪 Input & Output Files

- `dimension.in` — List of universes and connections
- `anomalies.in` — Known anomalies in each dimension
- `hub.in`, `spot.in`, etc. — Context-specific data for simulation
- `clusters.out`, `report.out`, etc. — Machine-generated results

---

## 📘 Java Classes Overview

- `BFS.java`, `DFS.java` — Graph traversal logic
- `TrackSpot.java` — Uses BFS to track character movement
- `CollectAnomalies.java` — Finds all reachable anomaly points
- `Clusters.java` — Implements connected component detection
- `CanonEvent.java` — Analyzes narrative-based multiversal logic
- `SaveMiles.java`, `Collider.java` — Special simulations

---

## 🛠️ Tools Used

- **Language**: Java
- **IDE**: VS Code 
- **No external libraries used** — All logic is manually implemented

---

## 🤝 Author
Developed independently to apply and demonstrate knowledge of **data structures, algorithms, OOP, and simulation modeling** in a creative universe.
