import 'package:flutter/material.dart';

class ListsWidget extends StatelessWidget{
  final List<String> items = ["Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4"];

  @override
  Widget build(BuildContext context){

    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              Text('Listas', style: TextStyle(fontSize: 16)),
              Text('Muestran conjuntos de elementos de manera organizada'),
            ],
          ),
        ),
        Expanded(
          child: ListView.builder(
            itemCount: items.length,
            itemBuilder: (_, index) => ListTile(
              title: Text(items[index]),
              leading: Icon(Icons.list),
            ),
          ),
        ),
      ],
    );
  }
}