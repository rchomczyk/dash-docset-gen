# dash-docset-gen
Simple and lightweight docset generator for the documentation made with Javadocs, currently it only supports the generation of the English localized documentations, because of the really weird reading process.

## Disclaimer
This project isn't ready to use on production, because it is just a side project, which was made only for generating the documentation for my own purposes.

## Usage
### Information
- You need to provide the absolute path of source and target directories, becasue currently we are doing a little of magic inside internals to get the correct paths.

```bash
$ java -jar dash-docset-gen-*.jar --name ExampleDockset-1_0_0 --source C:/Users/example/example-dir/javadocs --target C:/Users/example/example-dir/ExampleDockset-1_0_0.dockset
```

## Contributing
If you wish to fix or add new features, please, feel free to fork the project and send a pull request. Also, you could create an issue and start the discussion about the feature you want to add or modify.

## Docsets generated with this tool
- The documentation of [Spigot](https://github.com/Kapeli/Dash-User-Contributions/pull/3716)
- The documentation of [Purpur](https://github.com/Kapeli/Dash-User-Contributions/pull/3718)
- The documentation of [Velocity](https://github.com/Kapeli/Dash-User-Contributions/pull/3717)
- and more...

## License
[dash-docset-gen](./README.md) project is released under the [GNU GPL v3](./README.md)