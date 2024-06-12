from flask import Flask, request, jsonify, render_template
from flask_restful import Api, Resource

app = Flask(__name__)
api = Api(app)

bluetooth_data = {
    'humidity': {'task': None},
    'temperature': {'task': None},
    'gas': {'task': None}
}

@app.route('/bluetooth_data', methods=['POST'])
def receive_bluetooth_data():
    try:
        data = request.get_json()
        print("Received Bluetooth Data:", data)
        tmp = data.split(',')
        bluetooth_data['humidity']['task'] = tmp[0]
        bluetooth_data['temperature']['task'] = tmp[1]
        bluetooth_data['gas']['task'] = tmp[2]
        
        print(f'습도 = {bluetooth_data["humidity"]["task"]}, 온도 = {bluetooth_data["temperature"]["task"]}, 가스 농도 = {bluetooth_data["gas"]["task"]}')
        
        return jsonify({'status': 'success'})
    except Exception as e:
        print(f"Error: {e}")
        return jsonify({'status': 'fail', 'message': str(e)}), 400

@app.route('/see_data', methods=['GET'])
def see_data():
    return jsonify(bluetooth_data)

@app.route('/')
def index():
    return render_template('index.html', bluetooth_data=bluetooth_data)

class BluetoothDataList(Resource):
    def get(self):
        return jsonify(bluetooth_data)

api.add_resource(BluetoothDataList, '/bluetooth_data_list/', endpoint='bluetooth_data_list')

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
