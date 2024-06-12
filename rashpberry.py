import bluetooth
import requests

target_address = "98:DA:60:0B:C8:AC"  # 대상 Bluetooth 장치의 MAC 주소
port = 1  # RFCOMM 포트 번호

def receive_message(sock):
    while True:
        try:
            data = sock.recv(1024)
            if data:
                print("Received:", data.decode())
                data_val = data.decode().strip().split(',')
                data1 = data_val[0]
                data2 = data_val[1]
                data3 = data_val[2]
                print(f"Data1: {data1}, Data2: {data2}, Data3: {data3}")
                tmp = data1 + ',' + data2 + ',' + data3
               
                # Flask 서버에 데이터 전송
                send_to_flask_server(tmp)
        except KeyboardInterrupt:
            break

def send_message(sock, message):
    try:
        sock.send(message)
        print("Sent:", message.decode())
    except Exception as e:
        print("Failed to send message:", e)

def send_to_flask_server(data):
    try:
        url = 'http://220.68.27.127:5000/bluetooth_data'
        headers = {'Content-Type': 'application/json'}
        response = requests.post(url, json=data, headers=headers)
        print("Response from Flask server:", response.json())
    except Exception as e:
        print("Failed to send data to Flask server:", e)

def main():
    nearby_devices = bluetooth.discover_devices(duration=8, lookup_names=True, flush_cache=True)

    # 타겟 디바이스 검색
    if target_address not in [addr for addr, _ in nearby_devices]:
        print("Cannot find target device")
        return

    print("Target device found successfully")

    sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
    sock.connect((target_address, port))
    print("Bluetooth connection established")

    try:
        receive_message(sock)
    except Exception as e:
        print("Error:", e)
    finally:
        sock.close()

if __name__ == "__main__":
    main()