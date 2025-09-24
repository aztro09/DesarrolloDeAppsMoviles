import 'package:flutter/material.dart';
import 'second_screen.dart';

class HomeScreen extends StatelessWidget{
  const HomeScreen({super.key});

  @override Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(title: Text('Bienvenido')),
      body: Center(
        child: ElevatedButton(
            child: Text('Explorar elementos de UI'),
            onPressed: (){
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SecondScreen()),
              );
            },
        ),
      ),
    );
  }
}