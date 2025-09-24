import 'package:flutter/material.dart';

class TextFieldsWidget extends StatefulWidget{
  const TextFieldsWidget({super.key});

  @override
  State<TextFieldsWidget> createState() => _TextFieldsWidgetState();
}

class _TextFieldsWidgetState extends State<TextFieldsWidget>{
  final TextEditingController _controller = TextEditingController();
  String _text = '';

  @override
  Widget build(BuildContext context){
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        children: [
          Text('TextFields', style: TextStyle(fontSize: 15)),
          Text('Permiten al usuario ingresar texto'),
          TextField(controller: _controller, decoration: InputDecoration(labelText: 'Escribe algo')),
          ElevatedButton(
              onPressed: () => setState(() => _text = _controller.text),
              child: Text('Mostrar Texto'),
          ),
          Text('Texto ingresado: $_text',)
        ],
      ),
    );
  }
}