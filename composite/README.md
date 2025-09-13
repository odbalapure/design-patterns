## Composite Design Pattern

A structural pattern that lets us treat individual objects and composition of objects uniformly.

It allows us to build tree like structures (eg: File system, UI heirarchies, Organizational charts). Where client can work with both single elements and group of elements using the same interface.

It is useful in situations:
- We need to represent part-whole heirarchies.
- Perform operations on leaf nodes and composite nodes in a consistent way.
- Avoid writing special case logic to distinguish b/w single and grouped objects.

## The issue

When designing such systems, developers often start with `if-else blocks` or `type checks` to handle individual items differently from collections. Eg: A `render()` method might have to check if the element is a button, panel or a container.

But as the structure grows in complexity:
- This approach becomes hard to scale.
- Violates the **open-closed** principle.
- Introduces tight coupling b/w client code and structure's internal composition.

## Solution

The composite pattern solves this by defining a common interface for all elements - whether they are leaves or composites. Each component can be treated the same way -- allowing the client to operate on complex structures as if they were simple objects.

## Use case

Imagine building a file explorer, eg: `Finder` in **MacOS** or `File Explorer` in **Windows**.

The system needs to represent:
- Files: simple items that have name and size.
- Folders: container that can hold files and other folders.

The goal is to support operations such as:
- `getSize()`: return total size of a file or a folder.
- `printStructure()`: print name of the item, including indentations to show heirarchy.
- `delete()`: delete a file or a folder and everything inside it.

A naive approach would be to create separate classes for File and Folder.

```java
class File {
    private String name;
    private int size;

    public int getSize() {
        return size;
    }

    public void printStructure(String indent) {
        System.out.println(indent + name);
    }

    public void delete() {
        System.out.println("Deleting file: " + name);
    }
}
```

```java
class Folder {
    private String name;
    private List<Object> contents = new ArrayList<>();

    public int getSize() {
        int total = 0;
        for (Object item : contents) {
            if (item instanceof File) {
                total += ((File) item).getSize();
            } else if (item instanceof Folder) {
                total += ((Folder) item).getSize();
            }
        }
        return total;
    }

    public void printStructure(String indent) {
        System.out.println(indent + name + "/");
        for (Object item : contents) {
            if (item instanceof File) {
                ((File) item).printStructure(indent + " ");
            } else if (item instanceof Folder) {
                ((Folder) item).printStructure(indent + " ");
            }
        }
    }

    public void delete() {
        for (Object item : contents) {
            if (item instanceof File) {
                ((File) item).delete();
            } else if (item instanceof Folder) {
                ((Folder) item).delete();
            }
        }
        System.out.println("Deleting folder: " + name);
    }
}
```

### What is wrong here?

#### 1. Reptitive type checks

All the operations requrie an `instanceof` check and downcasting -- leading to duplicate and fragile code.

#### 2. No shared abstraction

There's no common interface for File and Folder, which means you can't treat them uniformly. We can't write code like:

```java
List<FileSystemItem> items = List.of(file, folder);
for (FileSystemItem item : items) {
    item.delete();
}
```

#### 3. Violation of open-closed principle

To add a new item type eg: `shortcut` or `compressed folder`, we must modify existing logic in everywhere place where type checks happen -- increasing the risk of bugs and regression.

#### 4. Lack of recursive elegance

Deleting deeply nested folders or computing sizes across multiple level becomes a tangled mess of nested conditionals and recursive checks.

#### 5. What we really need

- Introduce a common interface eg: `FileSystemItem` for all components.
- All files and folders to treated uniformly via polymorphism.
- Enable folders to contain a list of same interfaces, supporting arbitray nesting.
- Make the system easy to extend -- new item types can be added w/o modifying existing logic.

## Applying the composite pattern

### Defining the component interface

Start with a common interface for all items in our file system, allowing both files and folders to be treated uniformly.

```java
interface FileSystemItem {
    int getSize();
    void printStructure(String indent);
    void delete();
}
```

This ensures that all file system items -- whether they are files or folders, expose the same behavior of the client.

### Create leaf class - File

```java
class File implements FileSystemItem {
    private final String name;
    private final int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void printStructure(String indent) {
        System.out.println(indent + "- " + name + " (" + size + " KB)");
    }

    @Override
    public void delete() {
        System.out.println("Deleting file: " + name);
    }
}
```

Each `File` is a leaf node. It doesn't contain any children.

### Create composite class - Folder

```java
class Folder implements FileSystemItem {
    private final String name;
    private final List<FileSystemItem> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void addItem(FileSystemItem item) {
        children.add(item);
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemItem item : children) {
            total += item.getSize();
        }
        return total;
    }

    @Override
    public void printStructure(String indent) {
        System.out.println(indent + "+ " + name + "/");
        for (FileSystemItem item : children) {
            item.printStructure(indent + "  ");
        }
    }

    @Override
    public void delete() {
        for (FileSystemItem item : children) {
            item.delete();
        }
        System.out.println("Deleting folder: " + name);
    }
}
```

The `Folder` class is a **composite**. It can contain both `File` and `Folder` instances, making it recursive and scalable.

```java
public class FileExplorerApp {
    public static void main(String[] args) {
        FileSystemItem file1 = new File("readme.txt", 5);
        FileSystemItem file2 = new File("photo.jpg", 1500);
        FileSystemItem file3 = new File("data.csv", 300);

        Folder documents = new Folder("Documents");
        documents.addItem(file1);
        documents.addItem(file3);

        Folder pictures = new Folder("Pictures");
        pictures.addItem(file2);

        Folder home = new Folder("Home");
        home.addItem(documents);
        home.addItem(pictures);

        System.out.println("---- File Structure ----");
        home.printStructure("");

        System.out.println("\nTotal Size: " + home.getSize() + " KB");

        System.out.println("\n---- Deleting All ----");
        home.delete();
    }
}
```

The output

```bash
---- File Structure ----
+ Home/
  + Documents/
    - readme.txt (5 KB)
    - data.csv (300 KB)
  + Pictures/
    - photo.jpg (1500 KB)

Total Size: 1805 KB

---- Deleting All ----
Deleting file: readme.txt
Deleting file: data.csv
Deleting folder: Documents
Deleting file: photo.jpg
Deleting folder: Pictures
Deleting folder: Home
```

With composite pattern, the file system is modeled the way is naturally works -- as a tree of items where some are leaves and others are containers. Each operation is now modular, recursive and extensible.

## What was acheived

- **Unified treatement**: Files and folders share a common interface, allowing polymorphism.
- **Clean recursion**: No `instaceof`, no casting -- just delegation.
- **Scalability**: Easily support deeply nested structure.
- **Maintainability**: Adding new file types is easy.
- **Extensibility**: New operations can be added via interface extension or visitor pattern.
