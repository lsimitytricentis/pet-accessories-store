#!/bin/bash

# Define the path to your command scripts directory
#COMMANDS_DIR="$HOME/my_commands" # Adjust this if your scripts are elsewhere
COMMANDS_DIR="."
# Check if Zenity is installed
if ! command -v zenity &> /dev/null
then
    echo "Zenity is not installed. Please install it to run this UI."
    echo "On Debian/Ubuntu: sudo apt install zenity"
    echo "On Fedora: sudo dnf install zenity"
    echo "On macOS (with Homebrew): brew install zenity"
    exit 1
fi

# Function to run a command in a new terminal window
# This is crucial for commands that produce output
run_in_new_terminal() {
    local script_path="$1"
    
    # Try common terminal emulators in order of preference
    # You might need to adjust this for your specific desktop environment
    if command -v gnome-terminal &> /dev/null; then
        gnome-terminal -- /bin/bash -c "$script_path; exec bash"
    elif command -v xterm &> /dev/null; then
        xterm -e "$script_path; exec bash"
    elif command -v konsole &> /dev/null; then
        konsole -e "$script_path; exec bash"
    elif command -v qterminal &> /dev/null; then
        qterminal -e "$script_path; exec bash"
    elif command -v lxterminal &> /dev/null; then
        lxterminal -e "$script_path; exec bash"
    else
        # Fallback if no specific terminal found - might just execute in background
        zenity --error --text="No suitable terminal emulator found to display command output. Running in background."
        "$script_path" &
    fi
}

# Loop to keep the menu open
while true; do
    # Display the Zenity list dialog
    CHOICE=$(zenity --list \
      --title="Tricentis SeaLights Instrumentation Process" \
      --text="Select a command to execute (or click 'Exit' to quit):" \
      --column="ID" --column="Command Description" \
      "1" "Restore POM" \
      "2" "Integrate Sealights Build Scanner" \
      "3" "Build Application" \
      "4" "Restore POM" \
      "5" "Integrate Sealights Test Listener" \
      "6" "Run Unit Tests" \
      "7" "Run Application" \
      --height=800 --width=600 \
      --cancel-label="Exit" # Customize the cancel button text
    )

    # Process the user's choice
    case "$CHOICE" in
    "1") run_in_new_terminal "$COMMANDS_DIR/restore_POM_from_backup.sh;exit" ;;
    "2") run_in_new_terminal "$COMMANDS_DIR/integrate_sealights_build_scanner.sh;exit" ;;
    "3") run_in_new_terminal "$COMMANDS_DIR/build_application.sh;exit" ;;
    "4") run_in_new_terminal "$COMMANDS_DIR/restore_POM_from_backup.sh;exit" ;;
    "5") run_in_new_terminal "$COMMANDS_DIR/integrate_sealights_test_listener.sh;exit" ;;
    "6") run_in_new_terminal "$COMMANDS_DIR/run_test.sh;exit" ;;
    "7") run_in_new_terminal "$COMMANDS_DIR/start_application.sh;exit" ;;
    "") # User clicked "Exit" or closed the dialog
        zenity --info --title="Exited" --text="No command selected or dialog cancelled. Exiting application."
        break # Exit the while loop
        ;;
      *) # This case handles if user selects a column header or double-clicks empty space, though --list usually prevents this
        zenity --warning --title="Invalid Selection" --text="Please select a valid command from the list."
        ;;
    esac
done
