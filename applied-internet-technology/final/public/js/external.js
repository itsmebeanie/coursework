			var container;
			var camera, scene, renderer;
			var PI2 = Math.PI * 2;
			var programFill = function ( context ) {

				context.beginPath();
				context.arc( 0, 0, 0.5, 0, PI2, true );
				context.fill();

			};
			init();
			animate();

			function getRandomColor(){
				var colors = [0x378ABF, 0x333, 0xfff];
				return colors[parseInt(Math.random()*colors.length)];
			}
			function init() {
				var container = document.getElementById('intro');


				// camera
				camera = new THREE.PerspectiveCamera( 70, window.innerWidth / window.innerHeight, 1, 10000 );
				camera.position.set( 0, 300, 500 );

				scene = new THREE.Scene();

				var sphere = new THREE.Mesh( new THREE.SphereGeometry( 100, 100, 100) );
			var geometry = new THREE.BoxGeometry( 50, 50, 50 );

				for ( var i = 0; i < 80; i ++ ) {

					var object = new THREE.Mesh( geometry, new THREE.MeshBasicMaterial( { color: getRandomColor(), opacity: .5, wireframe:false,overdraw:0} ) );
					object.position.x = Math.random() * 800 - 400;
					object.position.y = Math.random() * 800 - 400;
					object.position.z = Math.random() * 800 - 400;

					object.scale.x = Math.random() * 2 + 1;
					object.scale.y = Math.random() * 2 + 1;
					object.scale.z = Math.random() * 2 + 1;

					object.rotation.x = Math.random() * 2 * Math.PI;
					object.rotation.y = Math.random() * 2 * Math.PI;
					object.rotation.z = Math.random() * 2 * Math.PI;

					scene.add( object );
				}


				/*for ( var i = 0; i < 150; i ++ ) {
					var particle = new THREE.Sprite( new THREE.SpriteCanvasMaterial( { color: getRandomColor(), program: programFill, opacity:1 } ) );
					particle.position.x = Math.random() * 800 - 200;
					particle.position.y = Math.random() * 800 - 200;
					particle.position.z = Math.random() * 800 - 200;
					particle.scale.y = Math.random() * 40+ 1;
					particle.scale.x = particle.scale.y = Math.random() * 40 + 40;
					scene.add( particle );

				}
				scene.add(sphere);*/

				renderer = new THREE.CanvasRenderer();
				renderer.setClearColor( 0xfffffffff );

				renderer.setPixelRatio( window.devicePixelRatio );
				renderer.setSize( window.innerWidth, window.innerHeight );
				// in order to render three js
				container.appendChild( renderer.domElement );

				window.addEventListener('resize', onWindowResize, false );

			}

			function onWindowResize() {
				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();
				renderer.setSize( window.innerWidth, window.innerHeight );

			}


			//

			function animate() {
				requestAnimationFrame( animate );
				render();

			}
			var radius = 600;
			var theta = 0;

			function render() {

				// rotate camera

				theta += 0.1;
				camera.position.x = radius * Math.sin( THREE.Math.degToRad( theta ) );
				camera.position.y = radius * Math.sin( THREE.Math.degToRad( theta ) );
				camera.position.z = radius * Math.cos( THREE.Math.degToRad( theta ) );
				camera.lookAt( scene.position );

				camera.updateMatrixWorld();


				renderer.render( scene, camera );

			}