# Dev Container Setup

This repository includes a complete dev container configuration for a consistent Java development environment.

## Quick Start

### Prerequisites
- Docker Desktop installed and running
- VS Code with "Dev Containers" extension

### Option 1: VS Code (Recommended)
1. Clone this repository
2. Open folder in VS Code
3. When prompted, click "Reopen in Container"
   - Or: Command Palette (Cmd+Shift+P) → "Dev Containers: Reopen in Container"
4. Wait for container to build (~3-5 minutes first time)
5. Done! Java 21 and all tools ready

### Option 2: Command Line
```bash
# Install devcontainer CLI
npm install -g @devcontainers/cli

# Clone and start
git clone https://github.com/YOUR_USERNAME/c1-codesignal-drills.git
cd c1-codesignal-drills
devcontainer up --workspace-folder .

# Access the container
docker exec -it $(docker ps --filter "label=devcontainer.local_folder=$(pwd)" -q) /bin/bash
```

## What's Inside

- **Java 21** (OpenJDK) - Latest LTS with modern language features
- **SDKMAN** - Manage multiple Java versions easily
- **GitHub CLI** - For repository operations from terminal
- **VS Code Java Extensions** - Language Server, Debugger, Test Runner
- **Git** - Pre-configured with your identity

## Working with Java

### Compile and Run
```bash
# Compile a single file
javac exercises/01_two_sum.java

# Run it
java -cp exercises Solution

# Or compile and run together
cd exercises && javac 01_two_sum.java && java Solution
```

### Switch Java Versions
```bash
# List available versions
sdk list java

# Install a different version
sdk install java 17.0.10-open

# Switch to it
sdk use java 17.0.10-open

# Set as default
sdk default java 17.0.10-open
```

## Customization

Edit `.devcontainer/devcontainer.json` to:
- Add VS Code extensions
- Change Java version
- Add additional tools
- Configure environment variables

After changes, rebuild: Command Palette → "Dev Containers: Rebuild Container"

## Troubleshooting

**Container won't build:**
- Ensure Docker is running
- Check internet connection
- Try: "Dev Containers: Rebuild Container Without Cache"

**SDKMAN not found:**
- Restart terminal: `source ~/.sdkman/bin/sdkman-init.sh`

**GitHub CLI not authenticated:**
```bash
gh auth login
```

## Benefits

✅ **Portable** - Same environment on any machine (Mac/Windows/Linux)  
✅ **Isolated** - No conflicts with other Java projects  
✅ **Reproducible** - Everyone on the team has identical setup  
✅ **Fast setup** - Clone and code in minutes
