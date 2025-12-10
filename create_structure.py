import os

# Create Android project structure
base_path = r"c:\Users\shudi\Desktop\Andr App"

directories = [
    "app",
    "app\\src",
    "app\\src\\main",
    "app\\src\\main\\java",
    "app\\src\\main\\java\\com",
    "app\\src\\main\\java\\com\\leddisplay",
    "app\\src\\main\\java\\com\\leddisplay\\controller",
    "app\\src\\main\\java\\com\\leddisplay\\controller\\ui",
    "app\\src\\main\\java\\com\\leddisplay\\controller\\ui\\theme",
    "app\\src\\main\\java\\com\\leddisplay\\controller\\viewmodel",
    "app\\src\\main\\java\\com\\leddisplay\\controller\\data",
    "app\\src\\main\\java\\com\\leddisplay\\controller\\model",
    "app\\src\\main\\res",
    "app\\src\\main\\res\\values",
    "app\\src\\main\\res\\xml",
]

for directory in directories:
    dir_path = os.path.join(base_path, directory)
    os.makedirs(dir_path, exist_ok=True)
    print(f"Created: {dir_path}")

print("\nDirectory structure created successfully!")
