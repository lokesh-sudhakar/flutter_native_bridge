import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  static const platform = const MethodChannel('samples.flutter.dev/battery');

  String text = "default text";
  String _batteryPercentage = "Your battery percentage";

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    listenMethodCall();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("Battery level"),
        ),
        body: Container(
          child: Center(
            child: Column(
              children: <Widget>[
                RaisedButton(
                  child: Text("Open Android Screen "),
                  color: Colors.purple,
                  onPressed: () {
                    _openAndroidActivity("This is android activity \n you clicked purple button");
                  },
                ) ,
                Text(text,
                style: TextStyle(
                  color: Colors.black,
                  fontWeight: FontWeight.bold
                  ),
                ),
                RaisedButton(
                  child: Text("Open Android Screen"),
                  color: Colors.yellow,
                  onPressed: () {
                    _openAndroidActivity("This is android activity \n you clicked yellow button");
                  },
                ) ,
                Text(_batteryPercentage,
                  style: TextStyle(
                      color: Colors.black,
                      fontWeight: FontWeight.bold
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }

  void _openAndroidActivity(String message) async {
    String batterPercentage;
    try {
      await platform.invokeMethod('getBatteryLevel',{"text":message});
    } catch (error, stackTrace) {
      batterPercentage = "Failed to open activity";
    }
    setState(() {
      _batteryPercentage = batterPercentage;
    });
  }

  void listenMethodCall() {
    platform.setMethodCallHandler(
            (call)  {
          if (call.method == "openFlutter") {
            Map args = (call.arguments as Map);
            String text = args["text"];
            setState(() {
              this.text = text;
            });
            return Future.value(true);
          } else {
            return Future.value(false);
          }
        });
  }

}

