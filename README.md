# Neural Network
inspired by workshop with https://github.com/gannimet/

## Project Structure
### Overview
This is a Java-based neural network training and prediction application with a menu-driven console interface. The application allows users to configure neural networks, manage training data in an SQLite database, train networks using backpropagation, and make predictions.

### Package Structure (15.11.2025)

pl.jakubholik90/

├── Main.java                          # Application entry point
├── controllers/
│   └── NeuralNetworkController.java   # Factory for creating neural networks
├── database/
│   ├── DataRecord.java                # Record type for training data
│   ├── DataSet.java                   # Training data set metadata
│   └── DatabaseService.java           # SQLite database operations
├── dto/
│   ├── ActivationFunctionsEnum.java   # Enum mapping activation functions
│   └── NeuralNetworkConfig.java       # Configuration object for NN parameters
├── exceptions/
│   └── WrongInputSizeException.java   # Custom exception for size mismatches
├── menus/
│   ├── MenuAbstract.java              # Base class for all menus
│   ├── MenuItem.java                  # Single menu option record
│   ├── MenuTable.java                 # Collection of menu items
│   ├── MainMenu.java                  # Root navigation menu
│   ├── ModifyNNMenu.java              # Configure neural network structure
│   ├── ManageDBMenu.java              # CRUD operations on training data
│   ├── TrainNNMenu.java               # Training workflow and visualization
│   └── PredictionMenu.java            # Run predictions with trained network
├── neuralnetwork/
│   ├── ActivationFunctionInterface.java  # Functional interface for activations
│   ├── ActivationFunctions.java          # Static activation function implementations
│   ├── NeuralNetwork.java                # Core NN with feedforward & backprop
│   ├── NeuralNetworkTrainLog.java        # Training metrics storage
│   └── TrainingDataRecord.java           # Input/output pair for training
├── others/
│   ├── NumPyLike.java                 # Matrix/vector math utilities
│   ├── Utils.java                     # Display helper functions
│   └── JhTests.java                   # Development test harness
└── ui/
├── UI.java                        # Interface for UI implementations
├── ConsoleUI.java                 # Terminal-based UI with ASCII graphics
├── WebAppUI.java                  # Placeholder for future web UI
└── App.java                       # Application orchestrator

### Core Components
1. Neural Network Engine (neuralnetwork/)

Configurable layer structure with variable neurons per layer
Multiple activation functions: ReLU, Leaky ReLU, Sigmoid, Dummy (identity)
Feedforward propagation for predictions
Backpropagation with gradient descent for training
Training logs with error convergence tracking

2. Data Management (database/)

SQLite persistence for training datasets
CRUD operations on individual records and entire sets
Data validation ensuring consistency with network structure
Support for multiple named datasets

3. User Interface (ui/ and menus/)

Abstract menu system with template method pattern
Console-based visualization including ASCII network diagrams and error plots
Extensible UI interface for future implementations (web, GUI)

4. Configuration (dto/)

Centralized network configuration with sensible defaults
Runtime modification of structure, learning rate, iterations, and activation functions

### Key Features

Modify NN Settings: Adjust inputs, outputs, hidden layers, activation functions, learning rate, and iterations
Manage Database: Insert, update, delete, and organize training data into sets
Train NN: Select dataset, verify compatibility, execute training, view error convergence
Run Predictions: Input custom values and get network output
Visualization: ASCII representation of network structure and training progress plots
