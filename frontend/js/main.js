const API_BASE_URL = 'http://localhost:8080/api/blackjack';

// **References**
// Navigation
const homeBtn = document.getElementById('home-btn');
const blackjackBtn = document.getElementById('blackjack-btn');

// Views (pgs)
const homeView = document.getElementById('home-view');
const blackjackView = document.getElementById('blackjack-view');

// Chip
const chipAmountSpan = document.getElementById('chip-amount')

// Blackjack Game
const dealerHandDiv = document.getElementById('dealer-hand');
const playerHandDiv = document.getElementById('player-hand');
const dealerScoreSpan = document.getElementById('dealer-score');
const playerScoreSpan = document.getElementById('player-score');
const blackjackMessageDiv = document.getElementById('blackjack-message');

// Controls
const bettingControlsDiv = document.getElementById('betting-controls');
const actionControlsDiv = document.getElementById('action-controls');
const dealButtonDiv = document.getElementById('deal-button')
const betAmountInput = document.getElementById('bet-amount');
const dealBtn = document.getElementById('deal-btn');
const hitBtn = document.getElementById('hit-btn');
const standBtn = document.getElementById('stand-btn');
const doubleBtn = document.getElementById('double-btn');

// API Functions
async function startGame(betAmount) {
    const response = await fetch(`${API_BASE_URL}/start?bet=${betAmount}`, {
        method: 'POST'
    });
    return await response.json();
}

async function playerHit() {
    const response = await fetch(`${API_BASE_URL}/hit`, {
        method: 'POST'
    });
    return await response.json();
}

async function playerStand() {
    const response = await fetch(`${API_BASE_URL}/stand`, {
        method: 'POST'
    });
    return await response.json();
}

async function playerDouble() {
    const response = await fetch(`${API_BASE_URL}/double`, {
        method: 'POST'
    });
    return await response.json();
}

// UI Update
function getCardImageFilename(card) {
    // Map the Java card object to corresponding image filename
    let rank = card.rank;
    const suitSymbol = card.suit;

    const suitConversion = {
        '♠': 'S',
        '♥': 'H',
        '♦': 'D',
        '♣': 'C'
    };
    const suitLetter = suitConversion[suitSymbol];

    if (rank == '10') rank = 'T';
    const suit = card.suit.charAt(0);
    return `${rank}${suitLetter}.png`;
}

// Render hand of cards to DOM
function renderHand(element, hand, isPlayerTurn, isDealer) {
    element.innerHTML = '';
    hand.forEach((card, index) => {
        const cardImage = document.createElement('img');
        
        // Either show front or back
        if (isDealer && isPlayerTurn && index === 0) {
            cardImage.src = 'assets/cards/BACK.png';
        } else {
            cardImage.src = `assets/cards/${getCardImageFilename(card)}`;
        }
        
        cardImage.classList.add('card');
        element.appendChild(cardImage);
    });
}

// Update all visable UI based on gameState backend
function updateUI(gameState) {
    const isPlayerTurn = gameState.gameStatus === 'PLAYER_TURN';
    
    renderHand(playerHandDiv, gameState.playerHand, isPlayerTurn, false);
    renderHand(dealerHandDiv, gameState.dealerHand, isPlayerTurn, true);

    playerScoreSpan.textContent = gameState.playerSum;

    dealerScoreSpan.textContent = isPlayerTurn ? '?' : gameState.dealerSum;

    // Chip balance
    chipAmountSpan.textContent = gameState.playerChipBalance;

    // Game message
    blackjackMessageDiv.textContent = gameState.gameStatus.replace(/_/g, ' ');

    // Show/hide + enable/disable controls
    const isGameOver = !isPlayerTurn;

    bettingControlsDiv.classList.toggle('hidden', !isGameOver);
    actionControlsDiv.classList.toggle('hidden', isGameOver);
    dealButtonDiv.classList.toggle('hidden', !isGameOver)

    // Disable double button if player !== 2 cards or can't afford it
    const canAffordDouble = gameState.playerChipBalance >= gameState.bet;
    doubleBtn.disabled = gameState.playerHand.length !== 2 || !canAffordDouble;
}

// Event listeners
dealBtn.addEventListener('click', async () => {
    const bet = parseInt(betAmountInput.value);
    if (isNaN(bet) || bet <= 0) {
        blackjackMessageDiv.textContent = 'Invalid bet';
        return;
    }
    try {
        const gameState = await startGame(bet);
        updateUI(gameState);
    } catch (error) {
        console.error('Error starting game:', error);
        blackjackMessageDiv.textContent = `Failed to start game. Server may not be running\nError: ${error.message || error}`;

    }
});

hitBtn.addEventListener('click', async () => {
    const gameState = await playerHit();
    updateUI(gameState);
});

standBtn.addEventListener('click', async () => {
    const gameState = await playerStand();
    updateUI(gameState);
});

doubleBtn.addEventListener('click', async () => {
    try {
        const gameState = await playerDouble();
        updateUI(gameState);
    } catch (error) {
        const errorJson = await error.response.json(); // backend server throwing json error
        blackjackMessageDiv.textContent = errorJson.message || 'You cannot currently double';
    }
});

// Initially only show home view
blackjackView.classList.add('hidden');
homeView.classList.remove('hidden');

homeBtn.addEventListener('click', () => {
    blackjackView.classList.add('hidden');
    homeView.classList.remove('hidden');
});

blackjackBtn.addEventListener('click', () => {
    homeView.classList.add('hidden');
    blackjackView.classList.remove('hidden');
    // Set initial UI state for new game
    bettingControlsDiv.classList.remove('hidden');
    actionControlsDiv.classList.add('hidden');
});