import 'package:flutter/material.dart';

class ButtonsWidget extends StatefulWidget{
  const ButtonsWidget({super.key});

  @override
  State<ButtonsWidget> createState() => _ButtonsWidgetState();
}

class _ButtonsWidgetState extends State<ButtonsWidget>{
  String _message = '';

  @override
  Widget build(BuildContext context){
    return Column(
      children: [
        Text('Botones', style: TextStyle(fontSize: 16)),
        Text('Permiten ejecutar acciones indicadas al dar click en ellos'),
        ElevatedButton(
          onPressed: () => setState(() => _message = 'Boton presionado!'),
          child: Text('Presioname :)'),
        ),
        IconButton(
          icon: Icon(Icons.camera_alt),
          onPressed: () => setState(() => _message = 'ImageButton presionado'),
        ),
        Text(_message)
      ],
    );
  }
}