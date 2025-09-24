import 'package:flutter/material.dart';
import 'package:practica_1_flutter/widgets/info_widget.dart';
import '../widgets/textfields_widget.dart';
import '../widgets/buttons_widget.dart';
import '../widgets/selections_widget.dart';
import '../widgets/lists_widget.dart';


class SecondScreen extends StatelessWidget{

  final List<Widget> widgets = [
    TextFieldsWidget(),
    ButtonsWidget(),
    SelectionsWidget(),
    ListsWidget(),
    InfoWidget(),
  ];

  final List<String> titles = const [
    'TextFields',
    'Botones',
    'Selecciones',
    'Listas',
    'Informacion',
  ];

  @override
  Widget build(BuildContext context){
    return DefaultTabController(
        length: widgets.length,
        child: Scaffold(
          appBar: AppBar(
            title: Text('Elementos de UI'),
            bottom: TabBar(
              isScrollable: true,
              tabs: titles.map<Widget>((title) => Tab(text: title)).toList(),
            ),
          ),
          body: TabBarView(children: widgets),
        ),
    );
  }
}