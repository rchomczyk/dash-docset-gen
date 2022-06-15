## dash-docset-gen
Simple and lightweight tool, which basically parses the javadoc's documentation and generates the Docset's file with respect of [Dash's tutorial](https://kapeli.com/docsets#dashDocset)

### Information
- You need to provide the absolute path of source and target directories, becasue currently we are doing a little of magic inside internals to get the correct paths.

### Usage
```bash
$ java -jar dash-docset-gen-*.jar --name ExampleDockset-1_0_0 --source C:/Users/example/example-dir/javadocs --target C:/Users/example/example-dir/ExampleDockset-1_0_0.dockset
```

### Contributing
If you wish to fix or add new features, please, feel free to fork the project and send a pull request. Also, you could create an issue and start the discussion about the feature you want to add or modify.
