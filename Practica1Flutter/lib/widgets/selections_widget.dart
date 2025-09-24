import 'package:flutter/material.dart';

class SelectionsWidget extends StatefulWidget{
  const SelectionsWidget({super.key});

  @override
  State<SelectionsWidget> createState() => _SelectionsWidgetState();
}

class _SelectionsWidgetState extends State<SelectionsWidget>{
  bool _isChecked = false;
  String _radioValue = 'A';
  bool _switchValue = false;

  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: const EdgeInsets.all(16.0),
      children: [
        Text('Elementos de seleccion', style: TextStyle(fontSize: 16)),
        Text('Permiten elegir opciones o activar funciones. A continuacion se mostraran los diferentes tipos de selecciones.'),
        CheckboxListTile(
          title: Text('Opcion 1'),
          value: _isChecked,
          onChanged: (val) => setState(() => _isChecked = val!),
        ),
        RadioListTile(
          title: Text('Opcion A'),
          value: 'A',
          groupValue: _radioValue,
          onChanged: (val) => setState(() => _radioValue = val!),
        ),
        RadioListTile(
          title: Text('Opcion B'),
          value: 'B',
          groupValue: _radioValue,
          onChanged: (val) => setState(() => _radioValue = val!),
        ),
        SwitchListTile(
          title: Text('Activar funcion'),
          value: _switchValue,
          onChanged: (val) => setState(() => _switchValue = val),
          ),
        Text('CheckBox: ${_isChecked ? "Activado" : "Desactivado"}'),
        Text('Radio: $_radioValue'),
        Text('Switch: ${_switchValue ? "Activado" : "Desactivado"}'),
      ],
    );
  }
}