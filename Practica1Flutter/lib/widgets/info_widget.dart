import 'package:flutter/material.dart';

class InfoWidget extends StatefulWidget {
  const InfoWidget({super.key});

  @override
  State<InfoWidget> createState() => _InfoWidgetState();
}

class _InfoWidgetState extends State<InfoWidget>{
  bool _loading = false;

  void _simulateLoading() {
    setState(() => _loading = true);
    Future.delayed(Duration(seconds: 2), () {
      setState(() => _loading = false);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            const Text('Elementos de informacion', style: TextStyle(fontSize: 16)),
            const Text('Muestran contenido visual o estados de alguna accion, evento o solo informacion'),
            const Icon(Icons.info, size:48),
            const Text('Este es un TextView informativo'),

            ElevatedButton(
                onPressed: _simulateLoading,
                child: Text('Carga:')
            ),
            if(_loading) CircularProgressIndicator(),
          ],
        ),
    );
  }
}