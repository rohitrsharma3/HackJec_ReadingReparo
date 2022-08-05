import 'package:clipboard/clipboard.dart';
import 'package:flutter/material.dart';
import 'package:highlight_text/highlight_text.dart';
import 'package:speech_to_text/speech_to_text.dart' as stt;
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Voice',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const SpeechScreen(),
    );
  }
}

class SpeechScreen extends StatefulWidget {
  const SpeechScreen({Key? key}) : super(key: key);

  @override
  State<SpeechScreen> createState() => _SpeechScreenState();
}

class _SpeechScreenState extends State<SpeechScreen> {
   stt.SpeechToText _speech = stt.SpeechToText();
  bool _isListening = false;
  String _text = "Press the button to start speaking";  
  double _confidence = 1.0;
  String langId = 'en-US';
  static late String savedWords;
  @override
  void initState(){
    super.initState();
    _speech = stt.SpeechToText();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Confidence : ${(_confidence * 100).toStringAsFixed(1)}'),
        actions: [
          PopupMenuButton<String>(itemBuilder: (context) =>[
            PopupMenuItem(value: 'en-US', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("en-US")
              ],
            )
            ),
            PopupMenuItem(value: 'en-GB', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("en-UK")
              ],
            )
            ),
            PopupMenuItem(value: 'en-AUS', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("en-AU")
              ],
            )
            ),
            PopupMenuItem(value: 'hi-IN', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("Hindi")
              ],
            )
            ),
            PopupMenuItem(value: 'ta-IN', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("Tamil")
              ],
            )
            ),
            PopupMenuItem(value: 'te-IN', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("Telugu")
              ],
            )
            ),
            PopupMenuItem(value: 'mr-IN', 
            child: Row(
              children: [
                SizedBox(width: 10),
                Text("Marathi")
              ],
            )
            )
          ],
          onSelected: (value) => {
            langId = value
          })
        ],
      ),
      floatingActionButtonLocation : FloatingActionButtonLocation.centerFloat,
      floatingActionButton: FloatingActionButton(
      onPressed: _listen,
      child : Icon(_isListening ? Icons.mic : Icons.mic_none),
      
      ),
      
      body: SingleChildScrollView(reverse: true,child: Container(
        padding: const EdgeInsets.fromLTRB(30.0, 30.0, 30.0, 150.0),
        child: Text(_text),
        ),
      ),
      
    );
    
  }


 void _listen() async {

    if (!_isListening) {
      bool available = await _speech.initialize(
        onStatus: (val) => print('onStatus: $val to ${langId}'),
        onError: (val) => print('onError: $val'),
      );
      if (available) {
        setState(() => _isListening = true);
        _speech.listen(
          onResult: (val) => setState(() {
            _text = val.recognizedWords;
            if (val.hasConfidenceRating && val.confidence > 0) {
              _confidence = val.confidence;
            }
          }),
          localeId: langId,
        );
      }
    } else {
      setState(() => _isListening = false);
       FlutterClipboard.copy(_text);
       savedWords = _text;
      _speech.stop();
      Navigator.push(context, MaterialPageRoute(builder: (context) => const AssementRoute()));
      
    }
  }

}

class AssementRoute extends StatelessWidget{
    const AssementRoute({super.key});
  
    @override

    Widget build(BuildContext context){
      return Scaffold(
        appBar: AppBar(
          title: const Text('Assesement'),
          actions: [
          
            FloatingActionButton(
              onPressed: () {
              Navigator.pop(context, MaterialPageRoute(builder: (context) => const SpeechScreen()));
            },
            child: Icon(Icons.arrow_back_ios_new),
            ),
            
          ],

        ),
        body: Center(
          child: Container(
            margin: const EdgeInsets.all(10.0),
            
            child: Text(_SpeechScreenState.savedWords),
          ),
        )
      );
    }

  }
