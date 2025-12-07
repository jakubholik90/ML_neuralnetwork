# Neural Network
inspired by workshop with https://github.com/gannimet/

# Neural Network Training & Prediction Application

## 📋 Table of Contents
- [Project Description](#project-description)
- [Purpose and Goals](#purpose-and-goals)
- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Usage Guide](#usage-guide)
- [Configuration](#configuration)
- [Contributing](#contributing)

## 📝 Project Description

This is a comprehensive Java-based neural network application that provides a complete framework for building, training, and utilizing feedforward neural networks with backpropagation learning. The application features a user-friendly console interface with ASCII visualization capabilities and persistent storage for both training data and trained network models.

## 🎯 Purpose and Goals

The primary objectives of this project are to:

1. **Educational Tool**: Provide a clear, accessible implementation of neural network fundamentals including feedforward propagation and backpropagation
2. **Practical Application**: Enable users to train custom neural networks for various classification and regression tasks
3. **Data Management**: Offer robust database-backed storage for training datasets
4. **Visualization**: Present network structure and training progress through ASCII-based console graphics
5. **Model Persistence**: Allow saving and loading of trained networks for reuse

## ✨ Key Features

### Neural Network Engine
- **Configurable Architecture**: Define custom network structures with variable input, hidden, and output layers
- **Multiple Activation Functions**:
    - ReLU (Rectified Linear Unit)
    - Leaky ReLU
    - Sigmoid
    - Linear (Dummy/Identity)
- **Backpropagation Training**: Gradient descent-based learning with customizable learning rate (eta)
- **Training Logs**: Track error convergence across epochs with visual plotting

### Data Management
- **SQLite Database**: Persistent storage for training datasets
- **CRUD Operations**: Full create, read, update, delete functionality for data records
- **Dataset Organization**: Group training records into named datasets
- **Data Validation**: Verify dataset consistency and compatibility with network structure

### User Interface
- **Menu-Driven Navigation**: Intuitive console-based menus for all operations
- **ASCII Visualization**:
    - Network structure diagrams showing layer connections
    - Training error convergence plots
- **Interactive Workflows**: Step-by-step guidance through configuration and training

### Model Persistence
- **JSON Export**: Save trained networks with complete state (weights, biases, configuration)
- **Import Functionality**: Load previously trained models
- **Metadata Tracking**: Store training dataset name, user comments, and timestamps

## 🛠 Technology Stack

### Core Technologies
- **Java 23**: Modern Java features including records, pattern matching
- **Maven**: Dependency management and build automation

### Dependencies
- **SQLite JDBC (3.51.0.0)**: Embedded database for training data persistence
- **Gson (2.13.2)**: JSON serialization/deserialization for model export/import


### Architectural Patterns
- **MVC-inspired Architecture**: Separation of UI, business logic, and data layers
- **Template Method Pattern**: Abstract menu system with customizable implementations
- **Factory Pattern**: Neural network creation through controller
- **Strategy Pattern**: Pluggable activation functions via functional interfaces

## 📁 Project Structure

```
pl.jakubholik90/
│
├── Main.java                          # Application entry point
│
├── controllers/
│   ├── NeuralNetworkController.java   # Factory for creating/restoring networks
│   └── NeuralNetworkExportController.java # JSON import/export functionality
│
├── database/
│   ├── DataRecord.java                # Training data record (input/output pair)
│   ├── DataSet.java                   # Dataset metadata (name, size)
│   └── DatabaseService.java           # SQLite CRUD operations
│
├── dto/
│   ├── ActivationFunctionsEnum.java   # Enum mapping activation functions
│   ├── NeuralNetworkConfig.java       # Network configuration object
│   └── NeuralNetworkSnapshotRecord.java # Serializable network state
│
├── exceptions/
│   └── WrongInputSizeException.java   # Custom exception for dimension mismatches
│
├── menus/
│   ├── MenuAbstract.java              # Base class for menu system
│   ├── MenuItem.java                  # Single menu option
│   ├── MenuTable.java                 # Collection of menu items
│   ├── MainMenu.java                  # Root navigation menu
│   ├── ModifyNNMenu.java              # Configure network architecture
│   ├── ManageDBMenu.java              # Training data CRUD operations
│   ├── TrainNNMenu.java               # Training workflow and visualization
│   ├── PredictionMenu.java            # Run predictions with custom input
│   └── SaveLoadNNMenu.java            # Model persistence operations
│
├── neuralnetwork/
│   ├── ActivationFunctionInterface.java # Functional interface for activations
│   ├── ActivationFunctions.java         # Static activation implementations
│   ├── NeuralNetwork.java               # Core network with forward/backprop
│   ├── NeuralNetworkTrainLog.java       # Training metrics storage
│   └── TrainingDataRecord.java          # Input/output pair for training
│
├── others/
│   ├── NumPyLike.java                 # Matrix/vector math utilities
│   ├── Utils.java                     # Display helper functions
│   └── JhTests.java                   # Development test harness
│
└── ui/
    ├── UI.java                        # Interface for UI implementations
    ├── ConsoleUI.java                 # Terminal-based UI with ASCII graphics
    ├── WebAppUI.java                  # Placeholder for future web UI
    └── App.java                       # Application orchestrator
```

## 📖 Usage Guide

### Basic Workflow

1. **Configure Neural Network**
    - Navigate to "Modify NN Settings"
    - Define structure (inputs, hidden layers, outputs)
    - Set activation functions, learning rate, iterations

2. **Prepare Training Data**
    - Go to "Manage Database"
    - Create a dataset and insert training records
    - Each record contains input array and expected output array

3. **Train the Network**
    - Select "Train NN"
    - Choose your dataset
    - Verify data compatibility
    - Start training and monitor error convergence

4. **Make Predictions**
    - Navigate to "Run prediction"
    - Enter custom input values
    - View network output

5. **Save/Load Models**
    - Export trained networks to JSON files
    - Load previously trained models for continued use

### Example: XOR Problem

1. Configure network: 2 inputs, 3 hidden neurons, 1 output
2. Create training data:
    - (0,0) → (0)
    - (0,1) → (1)
    - (1,0) → (1)
    - (1,1) → (0)
3. Train for 5000 iterations with eta=0.3
4. Test with custom inputs

### Example: Binary Classification

Configure a 3-4-1 network to classify binary patterns:
- 3 input features
- 4 hidden neurons
- 1 output (probability/classification)

## 👨‍💻 Author

**Jakub Holik**
- Package: pl.jakubholik90

## 📅 Version History

- **Current Version**: 1.0-SNAPSHOT
- **Last Updated**: November 15, 2025

---

**Note**: This is an educational implementation. For production machine learning applications, consider established frameworks like TensorFlow, PyTorch, or Deeplearning4j.
